package com.example.demo.criptografia;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.security.*;

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
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC"); // Use o provedor Bouncy Castle
        cipher.init(Cipher.DECRYPT_MODE, chave);
        return cipher.doFinal(dadosCriptografados);
    }

    // Gerar um par de chaves assimétricas (RSA)
    public static KeyPair gerarParChavesAssimetricas() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC"); // Use o provedor Bouncy Castle
        keyGen.initialize(2048); // Tamanho da chave em bits (pode ser ajustado conforme necessário)
        return keyGen.generateKeyPair();
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
}
