package com.generation.blogpessoal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//transforma objeto em tabela
@Entity

//da o nome a tabela
@Table(name= "tb_postagem")
public class Postagem {
	
	//define coluna como chave primaria
	@Id
	//equivale ao auto_increment 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	//campo deve ser preenchido
	@NotNull
	
	//minimo e maximo de caracteres
	@Size(min = 5, max = 100, message="O campo deve ter no minimo 5 caracteres e no maximo 100.")
	public String titulo;
	
	@NotNull 
	public String texto;

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
	
	//relação entre tabelas
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
}
