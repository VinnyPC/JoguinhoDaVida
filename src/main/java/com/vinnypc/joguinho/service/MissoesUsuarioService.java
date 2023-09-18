package com.vinnypc.joguinho.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vinnypc.joguinho.model.Missao;
import com.vinnypc.joguinho.model.MissoesUsuario;
import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.model.UsuarioLogin;
import com.vinnypc.joguinho.repository.MissoesUsuarioRepository;

@Service
public class MissoesUsuarioService {

	@Autowired
	private MissoesUsuarioRepository missoesUsuarioRepository;

	public void ativarMissao(Usuario usuario, Missao missao, Date data) {
		MissoesUsuario missaoUsuario = new MissoesUsuario();

		missaoUsuario.setUsuario(usuario);
		missaoUsuario.setMissao(missao);
		missaoUsuario.setStatus(1);
		missaoUsuario.setCategoria(missao.getCategoria());
		missaoUsuario.setDataAtivacao(data);
		missaoUsuario.setNome(missao.getNome());
		missaoUsuario.setPontuacao(missao.getPontuacao());
		missaoUsuario.setDataVencimento(calculaPrazoMissao(data));

		missoesUsuarioRepository.save(missaoUsuario);
	}

	public Date calculaPrazoMissao(Date dataAtivacao) {
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(dataAtivacao.toInstant(), ZoneId.systemDefault());
		LocalDate dataAtivacaoLocal = zonedDateTime.toLocalDate();

		LocalDate dataVencimentoLocal = dataAtivacaoLocal.plusMonths(1);

		ZonedDateTime zonedDateTimeVencimento = dataVencimentoLocal.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTimeVencimento.toInstant());
	}
	
	
	// Exibir missão vencida ao usuário no frontend pra saber se ele concluiu usando esse metodo vvvvv
//	public void verificarMissaoComPrazoExpirado(UsuarioLogin usuarioLogin) {
//		ZonedDateTime dataAutenticacao = usuarioLogin.getDataAutenticacao();
//		List<MissoesUsuario> missaoVencidaList = missoesUsuarioRepository.findAll()
//	            .stream()
//	            .filter(missao -> {
//	                ZonedDateTime dataVencimento = missao.getDataVencimento()
//	                    .toInstant()
//	                    .atZone(ZoneId.of("America/Sao_Paulo"));
//	                
//	                return dataAutenticacao.isAfter(dataVencimento);
//	            })
//	            .collect(Collectors.toList());
//		
//		
//		
//		for (MissoesUsuario missao : missaoVencidaList) {
//            // Perguntar ao usuário se ele concluiu a missão
//            boolean usuarioConcluiuMissao = /* Logica q vou fazer ainda para pegar a resposta do usuário */;
	
	//TODO: Fazer um endpoint de post, que receba a resposta do usuario com o id da missao e se ele concluiu ou nao e armazenar isso de alguma forma no db
	//
//            
//            if (usuarioConcluiuMissao) {
//                // Registre a pontuação da missão no perfil do usuário
//                Usuario usuario = usuarioLogin.getUsuario();
//                int pontuacaoMissao = missao.getPontuacao();
//                usuario.setPontos(usuario.getPontos() + pontuacaoMissao);
//                usuarioRepository.save(usuario);
//            }else exclui missao da lista do usuario
//        }
		
	
	  }
