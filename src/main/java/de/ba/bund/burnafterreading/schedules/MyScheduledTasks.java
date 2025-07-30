package de.ba.bund.burnafterreading.schedules;

import de.ba.bund.burnafterreading.repo.NachrichtRepository;
import de.ba.bund.burnafterreading.model.Nachricht;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MyScheduledTasks {

    private final NachrichtRepository repository;

    public MyScheduledTasks(NachrichtRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void runEveryFiveMinutes() {
        LocalDateTime grenze = LocalDateTime.now().minusMinutes(5);
        List<Nachricht> alteNachrichten = repository.findByErstelltAmBefore(grenze);

        if (!alteNachrichten.isEmpty()) {
            repository.deleteAll(alteNachrichten);
            System.out.println(alteNachrichten.size() + " alte Nachrichten gelöscht.");
        } else {
            System.out.println("Keine alten Nachrichten gefunden.");
        }
    }
}