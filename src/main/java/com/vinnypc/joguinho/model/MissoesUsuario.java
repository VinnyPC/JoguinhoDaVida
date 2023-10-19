package com.vinnypc.joguinho.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_missoes_usuario")
public class MissoesUsuario extends MissaoBase {

	@ManyToOne
	@JsonIgnoreProperties("missoes_usuario")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "missao_id")
	private Missao missao;
	
	@NotNull(message = "O atributo status não pode estar nulo")
	@Min(0)
	@Max(1)
	private Integer status;
	
	@NotNull
	private Date dataAtivacao;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Missao getMissao() {
		return missao;
	}

	public void setMissao(Missao missao) {
		this.missao = missao;
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}
	
	

	

}
