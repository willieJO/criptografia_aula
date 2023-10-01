package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByEmailAndSenhaasimetrica(String email, String senhaAsimetrica);

}
