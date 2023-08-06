package com.vinnypc.joguinho.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.vinnypc.joguinho.model.MissoesUsuario;
import com.vinnypc.joguinho.repository.MissoesUsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/missoes-usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MissoesUsuarioController {

	@Autowired 
	private MissoesUsuarioRepository missoesUsuarioRepository;

	@GetMapping
	public ResponseEntity<List<MissoesUsuario>> getAll() {
		return ResponseEntity.ok(missoesUsuarioRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MissoesUsuario> getById(@PathVariable Long id) {
		return missoesUsuarioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping
	public ResponseEntity<MissoesUsuario> post(@Valid @RequestBody MissoesUsuario missoesUsuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(missoesUsuarioRepository.save(missoesUsuario));
	}

	@PutMapping
	public ResponseEntity<MissoesUsuario> put(@Valid @RequestBody MissoesUsuario missoesUsuario) {
		return missoesUsuarioRepository.findById(missoesUsuario.getId()).map(
				resposta -> ResponseEntity.status(HttpStatus.OK).body(missoesUsuarioRepository.save(missoesUsuario)))

				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<MissoesUsuario> missoesUsuario = missoesUsuarioRepository.findById(id);
		if(missoesUsuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		missoesUsuarioRepository.deleteById(id);
		
	}

}
