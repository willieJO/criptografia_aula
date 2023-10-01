package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
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
	private String senhasimetrica;
	private String senhaasimetrica;
	
    
    public String getChave_privada() {
		return privatekey;
	}
	public void setChave_privada(String chave_privada) {
		this.privatekey = chave_privada;
	}
	private String privatekey;
	private String publickey;
	public String getChave_publica() {
		return publickey;
	}
	public void setChave_publica(String chave_publica) {
		this.publickey = chave_publica;
	}
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
		return senhasimetrica;
	}
	public void setSenhaSimetrica(String senhasimetrica) {
		this.senhasimetrica = senhasimetrica;
	}
	public String getSenhaAsimetrica() {
		return senhaasimetrica;
	}
	public void setSenhaAsimetrica(String senhaasimetrica) {
		this.senhaasimetrica = senhaasimetrica;
	}
	
	
	
	
	
}
