package com.generation.blogpessoal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Transforma objeto em tabela
@Entity
//Da o nome para a tabela
@Table(name= "tb_postagem")
public class Postagem {
	
	//Define coluna como chave primaria
	@Id
	//Equivale ao auto_increment 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	//Define que o campo deve ser preenchido
	@NotNull
	//Define o minimo e o maximo de caracteres, além de uma mensagem
	@Size(min = 5, max = 100, message="O campo deve ter de 5 a 100 caracteres")
	public String titulo;
	
	@NotNull
	public String texto;
	
	//Estabelece a relação entre as tabelas (usuario)
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	//Estabele a relação entre as tabelas (tema)
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;

	@Temporal (TemporalType.TIMESTAMP)
	public Date data = new java.sql.Date(System.currentTimeMillis());

	//Getter e setter
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}


