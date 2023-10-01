package com.example.demo.criptografia;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CriptografiaUtil {

    // Inicialize o provedor Bouncy Castle
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // Gerar uma chave simétrica (AES) aleatória
    public static SecretKey gerarChaveSimetrica() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC"); // Use o provedor Bouncy Castle
        keyGen.init(256); // Tamanho da chave em bits (pode ser ajustado conforme necessário)
        return keyGen.generateKey();
    }

    // Criptografar dados usando uma chave simétrica (AES)
    public static byte[] criptografarSimetricamente(byte[] dados, SecretKey chave) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC"); // Use o provedor Bouncy Castle
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        return cipher.doFinal(dados);
    }

    // Descriptografar dados usando uma chave simétrica (AES)
    public static byte[] descriptografarSimetricamente(byte[] dadosCriptografados, SecretKey chave)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC"); 
        cipher.init(Cipher.DECRYPT_MODE, chave);
        return cipher.doFinal(dadosCriptografados);
    }

    // Gerar um par de chaves assimétricas (RSA)
    public static KeyPair gerarParChavesAssimetricas() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC"); // Use o provedor Bouncy Castle
        keyGen.initialize(2048); // Tamanho da chave em bits (pode ser ajustado conforme necessário)
        return keyGen.generateKeyPair();
    }
    public static KeyFactory  gerarKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyFactory  keyGen = KeyFactory.getInstance("RSA", "BC"); // Use o provedor Bouncy Castle
        return keyGen;
    }

    // Criptografar dados usando chave pública (RSA)
    public static byte[] criptografarAssimetricamente(byte[] dados, PublicKey chavePublica)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC"); // Use o provedor Bouncy Castle
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        return cipher.doFinal(dados);
    }

    // Descriptografar dados usando chave privada (RSA)
    public static byte[] descriptografarAssimetricamente(byte[] dadosCriptografados, PrivateKey chavePrivada)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC"); // Use o provedor Bouncy Castle
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        return cipher.doFinal(dadosCriptografados);
    }

    // Para chaves asimetricas o que é necessario para descriptografar
    public static String encodePrivateKey(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }
    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }
    public static PublicKey decodePublicKey(String publicKeyEncoded) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyEncoded);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey decodePrivateKey(String privateKeyEncoded) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyEncoded);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        return keyFactory.generatePrivate(keySpec);
    }
}
