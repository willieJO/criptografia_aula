package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String nome;
	private String email;
	private String senha;
	private String senhaSimetrica;
	private String senhaAsimetrica;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
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
	public String getSenhaSimetrica() {
		return senhaSimetrica;
	}
	public void setSenhaSimetrica(String senhaSimetrica) {
		this.senhaSimetrica = senhaSimetrica;
	}
	public String getSenhaAsimetrica() {
		return senhaAsimetrica;
	}
	public void setSenhaAsimetrica(String senhaAsimetrica) {
		this.senhaAsimetrica = senhaAsimetrica;
	}
	
	
	
	
	
}
