package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L; 
	
	private String userName;
	private String password;
	
	// Autoriza todos os privilégios de usuário
	private List <GrantedAuthority> authorities;
	
	//O que terá na classe model de usuario
	public UserDetailsImpl (Usuario usuario) {
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}
	
	//Usuarios com o mesmo nivel de privilegio 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return userName;
	}
	
	//Se conta está expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//Se conta está bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//Se crendencial está expirada 
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//Se conta está ativa (habilitada)
	@Override
	public boolean isEnabled() {
		return true;
	}
}



