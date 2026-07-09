package de.ba.bund.burnafterreading.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptionServiceTest {

    private AESEncryptionService encryptionService;

    // Ein gültiger 32-Byte-Schlüssel (256 Bit) für den Test
    private final String validBase64Key = Base64.getEncoder()
            .encodeToString("DasIstEinSuperGeheimerKeyMit32By".getBytes());

    @BeforeEach
    void setUp() {
        // Vor jedem Test eine frische Instanz erzeugen
        encryptionService = new AESEncryptionService();
    }

    @Test
    void init_ThrowsException_WhenKeyIsInvalid() {
        // Arrange: Einen zu kurzen Key setzen
        String invalidKey = Base64.getEncoder().encodeToString("ZuKurz".getBytes());
        ReflectionTestUtils.setField(encryptionService, "base64Key", invalidKey);

        // Act & Assert: Prüfen, ob die Exception wie erwartet fliegt
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.init();
        });

        assertEquals("Ungültige Schlüssellänge für AES-256!", exception.getMessage());
    }

    @Test
    void encryptAndDecrypt_ShouldReturnOriginalText() throws Exception {
        // Arrange: Service sauber aufsetzen
        ReflectionTestUtils.setField(encryptionService, "base64Key", validBase64Key);
        encryptionService.init();

        String plainText = "Streng geheime API-Daten für das Go-Backend";

        // Act: Verschlüsseln und direkt wieder entschlüsseln
        String encryptedText = encryptionService.encrypt(plainText);
        String decryptedText = encryptionService.decrypt(encryptedText);

        // Assert
        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText, "Der Text sollte verschlüsselt sein");
        assertEquals(plainText, decryptedText, "Der entschlüsselte Text muss dem Original entsprechen");
    }

    @Test
    void encrypt_ShouldGenerateDifferentCiphertexts_DueToRandomIV() throws Exception {
        // Arrange
        ReflectionTestUtils.setField(encryptionService, "base64Key", validBase64Key);
        encryptionService.init();

        String plainText = "Test-Payload für Python Skripte";

        // Act: Den gleichen Text zweimal verschlüsseln
        String encrypted1 = encryptionService.encrypt(plainText);
        String encrypted2 = encryptionService.encrypt(plainText);

        // Assert: Dank SecureRandom für den IV dürfen die Ergebnisse nicht identisch sein
        assertNotEquals(encrypted1, encrypted2, "Gleicher Klartext muss dank IV unterschiedliche Chiffrate ergeben");
    }
}