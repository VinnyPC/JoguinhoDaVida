package com.vinnypc.joguinho.model;

public class UsuarioLogin {

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private Integer pontos;
	private Integer nivelAtual;
	private String token;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
