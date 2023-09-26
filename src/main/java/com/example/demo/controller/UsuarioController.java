package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Usuario;
import com.example.demo.service.UsuarioService;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService _usuarioService;
	
	@PostMapping("CadastrarUsuario")
	private void CadastrarUsuario(@RequestBody Usuario usuario) {
		String a = "dsa";
	}
}
