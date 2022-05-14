package com.generation.blogpessoal.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import com.generation.blogpessoal.model.Usuario;

//Indica que a classe UsuarioRepositoryTest é uma classe de test, que vai rodar em uma porta aleatoria a cada teste realizado
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//Cria uma instancia de testes, que define que o ciclo de vida do teste vai respeitar o ciclo de vida da classe(será executado e resetado após o uso)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repository;
	
	//Ordem dos tributos em usuario
	@BeforeAll
	void start() {
		
		repository.save(new Usuario(0L, "Nome Um", "Nome_Um@gmail.com","12345","https://i.imgur.com/fhintxd.jpeg"));
		
		repository.save(new Usuario(0L, "Dois Nome", "NomeNome@gmail.com", "23451", "https://i.imgur.com/fhintxd.jpeg"));

		repository.save(new Usuario(0L, "Nome Três", "3Nome3@gmail.com", "34512", "https://i.imgur.com/fhintxd.jpeg"));

		repository.save(new Usuario(0L, "Nemo Quatro", "QuaNemoTro@gmail.com", "45123", "https://i.imgur.com/fhintxd.jpeg"));

	}
	
	//Função de teste
	@Test
	//Nome do teste
	@DisplayName("Teste que retorna 1 usuario")
	public void retornaUmUsuario() {
	  Optional<Usuario> usuario = repository.findByUsuario("Nome_Um@gmail.com");
	  assertTrue(usuario.get().getUsuario().equals("Nome_Um@gmail.com"));
	  
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void retornaTresUsuarios() {
		
		List<Usuario> listaDeUsuarios = repository.findAllByNomeContainingIgnoreCase("Nome");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Nome Um"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Dois Nome"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Nome Três"));
		
	}
	
	
	
	@AfterAll
	public void end() {
		repository.deleteAll();
	}


}
