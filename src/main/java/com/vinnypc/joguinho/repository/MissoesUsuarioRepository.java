package com.vinnypc.joguinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinnypc.joguinho.model.MissoesUsuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Date;

public interface MissoesUsuarioRepository extends JpaRepository<MissoesUsuario, Long> {

        @Modifying
        @Query("UPDATE MissoesUsuario m SET m.status = CASE WHEN m.dataVencimento < :dataAtual THEN 1 ELSE 0 END WHERE m.status = 1")
        void atualizarStatusMissoesVencidas(@Param("dataAtual") Date dataAtual);
    }

