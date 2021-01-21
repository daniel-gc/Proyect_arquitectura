package mx.tecnetia.architecture.security.utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.log4j.Log4j2;
 
@Log4j2
public class AES {
 
	private AES() {

	}
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
			key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
		catch (NoSuchAlgorithmException e) {
			log.error("Ocurrió la siguiente excepción en el método estático de AES: {}", e.getLocalizedMessage());
            e.printStackTrace();
        } 
    }
 
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			var ret = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
			log.info("Cadena a encriptar: {} . Cadena encriptada: {}", strToEncrypt, ret);
			return ret;
        } 
        catch (Exception e) 
        {
			log.error("Error al encriptar: {}", e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
			log.info("Cadena para desencriptar: {} ", strToDecrypt);
			var ret = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
			log.info("Cadena para desencriptar: {} . Cadena desencriptada: {}", strToDecrypt, ret);
			return ret;
        } 
        catch (Exception e) 
        {
			log.error("Error al desencriptar: {}", e.toString());
        }
        return null;
    }
}