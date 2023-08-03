package com.vinnypc.joguinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinnypc.joguinho.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
