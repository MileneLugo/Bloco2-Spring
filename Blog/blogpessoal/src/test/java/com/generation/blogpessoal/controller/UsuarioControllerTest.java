package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastra um usuario")
	public void criarUmUsuario() {

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Um Nome", "1Nome@gmail.com", "12345", "https://i.imgur.com/fhintxd.jpeg"));

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());

	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir a duplicação de usuario")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastraUsuario(
				new Usuario(0L, "Dois Nome", "Dois_Nome@gmail.com", "54321", "https://i.imgur.com/fhintxd.jpeg"));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Dois Nome", "Dois_Nome@gmail.com", "54321", "https://i.imgur.com/fhintxd.jpeg"));

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());

	}
	
	@Test
	@Order(3)
	@DisplayName("Altera um usuario")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService.cadastraUsuario(
				new Usuario(0L, "Nome Três", "Nome_3@gmail.com", "43215", "https://i.imgur.com/fhintxd.jpeg"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Nemo Tres", "Nemo_3@gmail.com",
				"43215", "https://i.imgur.com/fhintxd.jpeg");

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());

	}
	
	@Test
	@Order(4)
	@DisplayName("Mostrar todos os usuarios")
	public void mostrarTodosUsuarios() {

		usuarioService.cadastraUsuario(
				new Usuario(0L, "Nome Quatro", "QuaNomeTro@gmail.com", "32154", "https://i.imgur.com/fhintxd.jpeg"));

		usuarioService.cadastraUsuario(
				new Usuario(0L, "Quatro Nome", "TroNomeQua@gmail.com", "45123", "https://i.imgur.com/fhintxd.jpeg"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuarios/all",
				HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}
	
	@Test
	@Order(5)
	@DisplayName("Deleta um usuario")
	public void deletarUsuario() {

		usuarioService.cadastraUsuario(
				new Usuario(0L, "Cinco Nome", "5Nome5@gmail.com", "54325", "https://i.imgur.com/fhintxd.jpeg"));

		usuarioService.cadastraUsuario(
				new Usuario(0L, "Nome Cinco", "Nome_Cinco5@gmail.com", "52345", "https://i.imgur.com/fhintxd.jpeg"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuarios/1",
				HttpMethod.DELETE, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}


}
