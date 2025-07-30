package de.ba.bund.burnafterreading.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "nachricht")
public class Nachricht {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(name = "text", columnDefinition = "TEXT")
    private String inhalt;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime erstelltAm;
}
