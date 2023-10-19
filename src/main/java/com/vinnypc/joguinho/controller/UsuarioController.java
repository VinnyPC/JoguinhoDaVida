package com.vinnypc.joguinho.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.vinnypc.joguinho.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vinnypc.joguinho.model.UsuarioLogin;
import com.vinnypc.joguinho.model.Missao;
import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.repository.MissaoRepository;
import com.vinnypc.joguinho.repository.UsuarioRepository;
import com.vinnypc.joguinho.service.MissoesUsuarioService;
import com.vinnypc.joguinho.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MissoesUsuarioService missoesUsuarioService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MissaoRepository missaoRepository;
	
	
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		if(usuarioRepository.existsById(id)){
			Usuario usuario = this.usuarioService.findById(id);
			return ResponseEntity.ok().body(usuario);
		}else{
			throw new ObjectNotFoundException("O usuário não existe!");
		}

	}

//	@GetMapping("/{id}")
//	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
//		Usuario usuario = this.usuarioService.findById(id);
//
//		if (usuario != null) {
//			return ResponseEntity.ok().body(usuario);
//		} else {
//
//		}
//	}


	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		return usuarioService.autenticarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
    

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid Usuario usuario) {

		return usuarioService.cadastrarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
		
		return usuarioService.atualizarUsuario(usuario)
			.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.usuarioService.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
		
	@PostMapping("/{usuarioId}/ativarMissao/{missaoId}")
	public ResponseEntity<String> ativarMissao(@PathVariable Long usuarioId, @PathVariable Long missaoId) {
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Missao missao = missaoRepository.findById(missaoId).orElse(null);
        Date data = new Date();
        
        if (usuario != null && missao != null) {
            
            missoesUsuarioService.ativarMissao(usuario, missao, data);
            return ResponseEntity.ok("Missão ativada com sucesso para o usuário!");
        } else {
            return ResponseEntity.notFound().build();
        }
	}

}
