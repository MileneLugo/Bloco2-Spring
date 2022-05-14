package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	//Cadastra o usuario
	public Optional<Usuario>cadastraUsuario(Usuario usuario){
		//Se o usuario já existe no banco de dados
		if(repository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
	
			//Criptografa a senha 
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			//Salva o usuario e senha criptografada
			return Optional.of(repository.save(usuario));
	}
	
	//Criptografa a senha digitada
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
	};
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
       Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				return usuarioLogin;

			}
		}	
		
		return Optional.empty();
		
	}
	
		//Se senha digitada é a mesma do banco de dados
       private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBanco);

	}
       
       //Gera Token
       private String gerarBasicToken(String usuario, String senha) {

    	//Token   
   		String token = usuario + ":" + senha;
   		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
   		
   		//Cria TOken
   		return "Basic " + new String(tokenBase64);

   	}


	
}



