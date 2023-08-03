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

import com.vinnypc.joguinho.model.Missao;
import com.vinnypc.joguinho.repository.MissaoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/missoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MissaoController {
	
	@Autowired
	private MissaoRepository missaoRepository;
	
	@GetMapping
	public ResponseEntity<List<Missao>> getAll(){
		return ResponseEntity.ok(missaoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Missao> getById(@PathVariable Long id) {
		return missaoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Missao>> getByTitulo(@PathVariable String nome){
		return ResponseEntity.ok(missaoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Missao> post(@Valid @RequestBody Missao missao){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(missaoRepository.save(missao));
	}
	
	@PutMapping
	public ResponseEntity<Missao> put(@Valid @RequestBody Missao missao){
		return missaoRepository.findById(missao.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(missaoRepository.save(missao)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Missao> missao = missaoRepository.findById(id);
		
		if(missao.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}	
			missaoRepository.deleteById(id);
		
	}
	
	
	
	

}
