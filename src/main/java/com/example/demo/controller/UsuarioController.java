package com.example.demo.controller;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.hibernate.query.NativeQuery.ReturnProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public static String encryptionKey = "senha_segura";
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
    // para criptografia simetrica
	/*@PostMapping("RealizarLogin")
	private ResponseEntity<?> RealizarLogin(@RequestBody Usuario usuario) {
		try {
			// pego a senha do front end e criptografo ela
			byte[] senhaSimetricaCriptografada = CriptografiaUtil.criptografarSimetricamente(
	                usuario.getSenha().getBytes(), chaveSimetrica);
			// transforma em string / base64 format 
			usuario.setSenhaSimetrica(Base64.getEncoder().encodeToString(senhaSimetricaCriptografada));
			// busco no banco o email do usuario, pq lá vai te a senha criptografada dele
			Optional<Usuario> usuarioOptional = _usuarioRepository.findByEmail(usuario.getEmail());
    		if (usuarioOptional.isPresent()) {
				Usuario usuarioArmazenado = usuarioOptional.get();
				if (Arrays.equals(senhaSimetricaCriptografada, Base64.getDecoder().decode(usuarioArmazenado.getSenhaSimetrica()))) {
            		return ResponseEntity.ok(usuarioArmazenado);
        		} else {
        		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        		}
			} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
			}
		} catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
		}
	}*/
	// Para criptografia asimetrica
	@PostMapping("RealizarLogin")
	private ResponseEntity<?> RealizarLogin(@RequestBody Usuario usuario) {
		try {
			
			Optional<Usuario> usuarioOptional = _usuarioRepository.findByEmail(usuario.getEmail());
    		if (usuarioOptional.isPresent()) {
				Usuario usuarioArmazenado = usuarioOptional.get();
				String encryptedPassword = usuarioArmazenado.getSenhaAsimetrica();

				String privateKeyEncoded = usuarioArmazenado.getChave_privada(); 
				byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyEncoded);
				KeyFactory keyFactory = CriptografiaUtil.gerarKeyPair();
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
				PrivateKey privateKeyDoUsuario = keyFactory.generatePrivate(keySpec);

				byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedPassword);
				byte[] decryptedPasswordBytes = CriptografiaUtil.descriptografarAssimetricamente(encryptedPasswordBytes, privateKeyDoUsuario);
				String decryptedPassword = new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
				if (decryptedPassword.equals(usuario.getSenha())) {
					
					return ResponseEntity.ok(usuarioArmazenado);
				} else {
					
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
				}
				
			} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
			}
		} catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
		}
	}
	
	@PostMapping("CadastrarUsuario")
	private ResponseEntity<?> CadastrarUsuario(@RequestBody Usuario usuario) {
		Optional<Usuario> usuarioOptional = _usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ja registrado");
		}
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

				//Salvar a chave privada e publica no banco
				String publicKeyEncoded = Base64.getEncoder().encodeToString(parChavesAssimetricas.getPublic().getEncoded());
				String privateKeyEncoded = Base64.getEncoder().encodeToString(parChavesAssimetricas.getPrivate().getEncoded());
				usuario.setChave_privada(privateKeyEncoded);
				usuario.setChave_publica(publicKeyEncoded);
	            // Salvar o usuário no banco de dados
	            _usuarioRepository.save(usuario);
				return ResponseEntity.ok(null);
	        } catch (Exception e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	        }
	}
}
