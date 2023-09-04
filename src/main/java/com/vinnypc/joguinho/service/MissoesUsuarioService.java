package com.vinnypc.joguinho.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

}
