package com.vinnypc.joguinho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinnypc.joguinho.model.Missao;
import com.vinnypc.joguinho.repository.MissaoRepository;

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
	
	

}
