package com.vinnypc.joguinho.security;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(userName);
		if(Objects.isNull(usuario))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		
		return new UserDetailsImpl(usuario.get().getId(), usuario.get().getEmail(), usuario.get().getSenha(), usuario.get().getProfiles());
		
			
	}

}
