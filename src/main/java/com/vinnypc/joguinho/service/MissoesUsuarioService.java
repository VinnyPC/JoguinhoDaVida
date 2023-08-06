package com.vinnypc.joguinho.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinnypc.joguinho.model.Missao;
import com.vinnypc.joguinho.model.MissoesUsuario;
import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.repository.MissoesUsuarioRepository;

@Service
public class MissoesUsuarioService {
	
	@Autowired
	private MissoesUsuarioRepository missoesUsuarioRepository;
	
	public void ativarMissao(Usuario usuario, Missao missao) {
		MissoesUsuario missaoUsuario = new MissoesUsuario();
        missaoUsuario.setUsuario(usuario);
        missaoUsuario.setMissao(missao);
        missaoUsuario.setStatus(1);
        
        missaoUsuario.setCategoria(missao.getCategoria());
        missaoUsuario.setDataVencimento(missao.getDataVencimento());
        missaoUsuario.setNome(missao.getNome());
        missaoUsuario.setPontuacao(missao.getPontuacao());
        
        missoesUsuarioRepository.save(missaoUsuario);
	}

}
