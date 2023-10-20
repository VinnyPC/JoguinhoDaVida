package com.vinnypc.joguinho.model;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinnypc.joguinho.model.enums.ProfileEnum;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_usuario")
public class Usuario {

	public Usuario(Long id, String nome, String email, String senha, Integer pontos, Integer nivelAtual) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.pontos = pontos;
		this.nivelAtual = nivelAtual;
		
	}

	public Usuario() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O atributo nome do usuario é obrigatório")
	@Size(min = 2, max = 100, message = "O atributo nome do entidade usuario deve conter entre 2 e 100 caracteres")
	private String nome;

	@Schema(example = "email@email.com")
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
	
	
	private Date dataAutenticacao;

	@ElementCollection(fetch = FetchType.EAGER)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@CollectionTable(name = "user_profile")
	@Column(name = "profile", nullable = false)
	private Set<Integer> profiles = new HashSet<>();

	public Set<ProfileEnum> getProfiles() {
		return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
	}

	public void addProfile(ProfileEnum profileEnum) {
		this.profiles.add(profileEnum.getCode());

	}

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

	public void setProfiles(Set<Integer> profiles) {
		this.profiles = profiles;
	}

	public Date getDataAutenticacao() {
		return dataAutenticacao;
	}

	public void setDataAutenticacao(Date dataAutenticacao) {
		this.dataAutenticacao = dataAutenticacao;
	}
	
	

	
	
	
	
	

}
