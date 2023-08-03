package com.vinnypc.joguinho.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo nome do usuario é obrigatório")
	@Size(min = 2, max = 100, message = "O atributo nome do entidade usuario deve conter entre 2 e 100 caracteres")
	private String nome;
	
	@NotNull(message = "O Atributo Usuário é Obrigatório!")
	@Email(message = "O atributo email deve ser um email válido!")
	private String email;
	
	@NotBlank(message = "O Atributo Senha é Obrigatório!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;
	
	@PositiveOrZero(message = "O Atributo pontos da entidade usuario deve ser maior/igual a zero")
	private Integer pontos;
	
	@PositiveOrZero(message = "O Atributo nivel atual da entidade usuario deve ser maior/igual a zero")
	private Integer nivelAtual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

	public Integer getNivelAtual() {
		return nivelAtual;
	}

	public void setNivelAtual(Integer nivelAtual) {
		this.nivelAtual = nivelAtual;
	}
	
	


}
