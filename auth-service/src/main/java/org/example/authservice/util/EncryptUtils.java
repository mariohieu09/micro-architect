package org.example.authservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

@Component
@Converter
public class EncryptUtils implements AttributeConverter<String, String> {

    public static String MY_KEY = "trunghieu123";

    private static final String RSA_ALGORITHM = "RSA";

    private static final int KEY_SIZE = 2048;

    public final static PrivateKey PRIVATE_KEY;

    public final static PublicKey PUBLIC_KEY;
    static {
        try {
            KeyPair keyPair = generateKeyPair();
            PRIVATE_KEY = keyPair.getPrivate();
            PUBLIC_KEY = keyPair.getPublic();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    public static String AESEncrypt(String strToEncrypt, String secret) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key;
            if(secret == null) {
                key = MY_KEY.getBytes(StandardCharsets.ISO_8859_1);
            }else {
                key = secret.getBytes(StandardCharsets.ISO_8859_1);
            }
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public  static String AESDecrypt(String strToDecrypt, String secret) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key;
            if(secret == null) {
                 key = MY_KEY.getBytes(StandardCharsets.ISO_8859_1);
            }else {
                key = secret.getBytes(StandardCharsets.ISO_8859_1);
            }
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static KeyPair generateKeyPair() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    public static String RSAEncrypt(String strToEncrypt) throws Exception{
        Cipher c = Cipher.getInstance(RSA_ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, PRIVATE_KEY);
        byte[] encryptOut = c.doFinal(strToEncrypt.getBytes());
        return Base64.getEncoder().encodeToString(encryptOut);
    }

    public static String RSADecrypt(String strToDecrypt) throws Exception{
        Cipher c = Cipher.getInstance(RSA_ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, PUBLIC_KEY);
        byte[] decryptOut = c.doFinal(Base64.getDecoder().decode(strToDecrypt));
        return new String(decryptOut);
    }

    public static String RSAEncrypt(Object object) throws Exception{
        byte[] objectByte = SerializationUtils.serialize(object);
        Cipher c = Cipher.getInstance(RSA_ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, PRIVATE_KEY);
        return Base64.getEncoder().encodeToString(objectByte);
    }

    public static Object RSADecryptObject(String strToDecrypt) throws Exception{
        Cipher c = Cipher.getInstance(RSA_ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, PUBLIC_KEY);
        byte[] decryptOut = c.doFinal(Base64.getDecoder().decode(strToDecrypt));
        return SerializationUtils.deserialize(decryptOut);
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        return AESEncrypt(s, null);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return AESDecrypt(s, null);
    }
}
