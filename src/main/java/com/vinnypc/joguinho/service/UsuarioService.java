package com.vinnypc.joguinho.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.model.UsuarioLogin;
import com.vinnypc.joguinho.model.enums.ProfileEnum;
import com.vinnypc.joguinho.repository.UsuarioRepository;
import com.vinnypc.joguinho.security.JwtService;
import com.vinnypc.joguinho.security.UserDetailsImpl;
import com.vinnypc.joguinho.service.exceptions.DataBindingViolationException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Usuario findById(Long id) {
		UserDetailsImpl userDetailsImpl = authenticated();
		if (!Objects.nonNull(userDetailsImpl)
				|| !userDetailsImpl.hasRole(ProfileEnum.ADMIN) && !id.equals(userDetailsImpl.getId()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acesso negado!");

		Optional<Usuario> usuario = this.usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new ObjectNotFoundException(
				"Usuário não encontrado! Id: " + id + " tipo: " + Usuario.class.getName(), usuario));
	}

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		usuario.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));

		usuario.setPontos(0);
		usuario.setNivelAtual(0);

		return Optional.of(usuarioRepository.save(usuario));

	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (this.findById(usuario.getId()) != null) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByEmail(usuario.getEmail());

			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(usuarioRepository.save(usuario));

		}

		return Optional.empty();

	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getEmail(),
				usuarioLogin.get().getSenha());

		Authentication authentication = authenticationManager.authenticate(credenciais);

		if (authentication.isAuthenticated()) {

			Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLogin.get().getEmail());

			if (usuario.isPresent()) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setNivelAtual(usuario.get().getNivelAtual());
				usuarioLogin.get().setPontos(usuario.get().getPontos());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getEmail()));
				usuarioLogin.get().setSenha("");

				return usuarioLogin;

			}

		}

		return Optional.empty();

	}
	
	public void deletarUsuario(Long id) {
		findById(id);
		try {
			this.usuarioRepository.deleteById(id);
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

	public static UserDetailsImpl authenticated() {
		try {
			return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
