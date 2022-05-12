package com.generation.lojadegames.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.lojadegames.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
	
	// Usamos ao ter mais de uma resposta
	
	public Optional <Usuario> findByUsuario(String usuario);

}
