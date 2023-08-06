package com.vinnypc.joguinho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
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
	@JsonIgnoreProperties("missoes_usuario")
	private Usuario usuario;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	@NotNull(message = "O atributo status não pode estar nulo")
	@Min(0)
	@Max(1)
	private Integer status;

}
