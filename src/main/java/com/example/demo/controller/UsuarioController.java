package com.example.demo.controller;

import java.security.KeyPair;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Usuario;
import com.example.demo.criptografia.CriptografiaUtil;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {
	@Autowired
	private UsuarioRepository _usuarioRepository;
	private SecretKey chaveSimetrica; // Defina a chave simétrica
    private KeyPair parChavesAssimetricas; // Defina o par de chaves assimétricas
    
    @PostConstruct
    public void init() {
        try {
            // Gerar a chave simétrica
            chaveSimetrica = CriptografiaUtil.gerarChaveSimetrica();

            // Gerar o par de chaves assimétricas
            parChavesAssimetricas = CriptografiaUtil.gerarParChavesAssimetricas();
        } catch (Exception e) {
            // Lide com exceções aqui
        }
    }
    
	@PostMapping("CadastrarUsuario")
	private void CadastrarUsuario(@RequestBody Usuario usuario) {
		 try {
			  String senhaOriginal = usuario.getSenha();

	            // Criptografar a senha simetricamente
	            byte[] senhaSimetricaCriptografada = CriptografiaUtil.criptografarSimetricamente(
	                senhaOriginal.getBytes(), chaveSimetrica);

	            // Criptografar a senha assimetricamente
	            byte[] senhaAssimetricaCriptografada = CriptografiaUtil.criptografarAssimetricamente(
	                senhaOriginal.getBytes(), parChavesAssimetricas.getPublic());

	            // Armazenar as senhas criptografadas nos atributos correspondentes
	            usuario.setSenhaSimetrica(Base64.getEncoder().encodeToString(senhaSimetricaCriptografada));
	            usuario.setSenhaAsimetrica(Base64.getEncoder().encodeToString(senhaAssimetricaCriptografada));

	            // Armazenar a senha original (não criptografada) no atributo senha
	            usuario.setSenha(senhaOriginal);

	            // Salvar o usuário no banco de dados
	            _usuarioRepository.save(usuario);
	        } catch (Exception e) {
	            // Lide com exceções aqui
	        }
	}
}
