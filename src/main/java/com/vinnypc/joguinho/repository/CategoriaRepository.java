package com.vinnypc.joguinho.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.vinnypc.joguinho.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	public List <Categoria> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
