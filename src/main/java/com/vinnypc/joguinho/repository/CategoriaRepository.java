package com.vinnypc.joguinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinnypc.joguinho.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
