package de.ba.bund.burnafterreading.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESEncryptionService {

    @Value("${encryption.aes-key}")
    private String base64Key;

    private SecretKeySpec secretKey;
    private static final int IV_LENGTH = 16;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        if (decodedKey.length != 32) {
            throw new IllegalArgumentException("Ungültige Schlüssellänge für AES-256!");
        }
        this.secretKey = new SecretKeySpec(decodedKey, "AES");
    }

    public String encrypt(String plainText) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Cipher initialisieren
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // Daten verschlüsseln
        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        // IV + verschlüsselte Daten zusammenfügen und Base64-encodieren
        byte[] combined = new byte[IV_LENGTH + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, combined, IV_LENGTH, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);

        // IV und verschlüsselte Daten extrahieren
        byte[] iv = new byte[IV_LENGTH];
        byte[] encrypted = new byte[combined.length - IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
        System.arraycopy(combined, IV_LENGTH, encrypted, 0, encrypted.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Cipher initialisieren
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        // Entschlüsseln
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
}
