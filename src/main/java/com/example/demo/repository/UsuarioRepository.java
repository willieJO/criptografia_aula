package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
	public Optional<Usuario> findByEmail(String email);
}
