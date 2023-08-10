package com.vinnypc.joguinho.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vinnypc.joguinho.model.Usuario;
import com.vinnypc.joguinho.repository.UsuarioRepository;
import com.vinnypc.joguinho.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", 0, 0));
	}

	@Test
	@DisplayName("Cadastrar Usuário")
	public void criarUsuarioTest() {
		HttpEntity<Usuario> requestBody = new HttpEntity<Usuario>(
				new Usuario(0L, "Usuario Teste", "usuario_teste@email.com", "123456789", 0, 0));

		ResponseEntity<Usuario> responseBody = testRestTemplate.exchange("/usuario/cadastrar", HttpMethod.POST,
				requestBody, Usuario.class);

		assertEquals(HttpStatus.CREATED, responseBody.getStatusCode());
	}

	@Test
	@DisplayName("Duplicação de Usuário")
	public void duplicaUsuarioTest() {
		usuarioService.cadastrarUsuario(
				new Usuario(0L, "Usuario Duplicado", "usuario_duplicadoteste@email.com", "123456789", 0, 0));

		HttpEntity<Usuario> requestBody = new HttpEntity<Usuario>(
				new Usuario(0L, "Usuario Duplicado", "usuario_duplicadoteste@email.com", "123456789", 0, 0));

		ResponseEntity<Usuario> responseBody = testRestTemplate.exchange("/usuario/cadastrar", HttpMethod.POST,
				requestBody, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseBody.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar Usuário")
	public void atualizaUsuarioTest() {
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(
				new Usuario(0L, "Usuario Criado", "usuario_criadoteste@email.com", "123456789", 0, 0));

		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Usuario Atualizado2",
				"usuario_atualizado2teste@email.com", "123456789", 0, 0);

		HttpEntity<Usuario> requestBody = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> responseBody = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuario/atualizar", HttpMethod.PUT, requestBody, Usuario.class);

		assertEquals(HttpStatus.OK, responseBody.getStatusCode());
	}
	
	

	@Test
	@DisplayName("Listar todos os Usuarios")
	public void listarUsuariosTest() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "Usuario 1", "usuario_1teste@email.com", "123456789", 0, 0));

		usuarioService.cadastrarUsuario(new Usuario(0L, "Usuario 2", "usuario_2teste@email.com", "123456789", 0, 0));

		ResponseEntity<String> response = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuario/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}







