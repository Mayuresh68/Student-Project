package com.mayuresh.student.Util;

import java.util.Base64;
//import org.apache.tomcat.util.codec.binary.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptionDecryption {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
    private static final String SECRET_KEY = "8080808080808080";
    private static final String IV = "8080808080808080";

    //    @Value("${encryption.decryption.status}")
    private String encryptionDecryptionValue = "true";

    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (encryptionDecryptionValue.contentEquals("true")) {
            try{
                SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

                Cipher cipher = null;
                cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

                byte[] encryptedBytes = null;

                encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

                String base64Encoded = Base64.getEncoder().encodeToString(encryptedBytes);

                // Replace characters as per your original TypeScript code
                String result = base64Encoded.replace("+", "-").replace("/", "_");
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return data;
    }


    public String decrypt(String data) {
        try {
            // Replace characters as per your original TypeScript code
            String changestheforma1 = data.replace('-', '+').replace('_', '/');

            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(changestheforma1));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public String convertToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(object));
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // handle the exception according to your needs
        }
    }
}
