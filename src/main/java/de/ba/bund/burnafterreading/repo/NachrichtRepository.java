package de.ba.bund.burnafterreading.repo;

import de.ba.bund.burnafterreading.model.Nachricht;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NachrichtRepository extends JpaRepository<Nachricht, UUID> {
    List<Nachricht> findByErstelltAmBefore(LocalDateTime zeitpunkt);
}
