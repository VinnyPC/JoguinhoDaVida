package com.vinnypc.joguinho.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.vinnypc.joguinho.model.Missao;

public interface MissaoRepository extends JpaRepository<Missao, Long> {
	
	public List <Missao> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

}
