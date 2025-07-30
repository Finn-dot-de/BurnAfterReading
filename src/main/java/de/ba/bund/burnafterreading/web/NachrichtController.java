package de.ba.bund.burnafterreading.web;

import de.ba.bund.burnafterreading.model.Nachricht;
import de.ba.bund.burnafterreading.repo.NachrichtRepository;
import de.ba.bund.burnafterreading.service.AESEncryptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notes")
@Tag(name = "Nachrichten", description = "REST API für geheime Einmal-Nachrichten ('Burn after Reading')")
public class NachrichtController {

    private final NachrichtRepository repository;

    private final AESEncryptionService encryptionService;

    @PostMapping
    @Operation(summary = "Neue Nachricht speichern", description = """
            Verschlüsselt und speichert eine neue Nachricht. Die Rückgabe ist die Nachricht-ID (UUID),\s
            mit der die Nachricht später **einmalig abgerufen und dabei gelöscht** werden kann.
           \s""")
    public ResponseEntity<String> speichern(@RequestBody String inhalt) {
        Nachricht n = new Nachricht();
        try {
            n.setInhalt(encryptionService.encrypt(inhalt));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Fehler bei der Verschlüsselung: " + e.getMessage());
        }
        n.setErstelltAm(LocalDateTime.now());
        repository.save(n);
        return ResponseEntity.ok(n.getId().toString());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Nachricht lesen (nur Entwicklung)", description = """
            Gibt den entschlüsselten Inhalt der Nachricht zurück – **ohne** sie zu loeschen.
            """)
    public ResponseEntity<String> lesen(@PathVariable UUID id) {
        Optional<Nachricht> n = repository.findById(id);
        return n.map(nachricht -> {
            try {
                return ResponseEntity.ok(encryptionService.decrypt(nachricht.getInhalt()));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Fehler bei der Entschlüsselung: " + e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = "text/plain")
    @Operation(summary = "Nachricht lesen und loeschen ('Burn after Reading')", description = """
            Ruft eine Nachricht **einmalig** ab und löscht sie danach sofort.
            
            🕵️‍♂️ **'Burn after Reading'** – die Nachricht kann danach nicht mehr abgerufen werden!
            """)
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Nachricht entschlüsselt und gelöscht"), @ApiResponse(responseCode = "404", description = "Nachricht nicht gefunden"), @ApiResponse(responseCode = "500", description = "Fehler beim Entschlüsseln")})
    public ResponseEntity<String> loeschen(@PathVariable UUID id) {
        Optional<Nachricht> n = this.repository.findById(id);
        this.repository.deleteById(id);
        return n.map(nachricht -> {
            try {
                return ResponseEntity.ok(encryptionService.decrypt(nachricht.getInhalt()));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Fehler bei der Entschlüsselung: " + e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
