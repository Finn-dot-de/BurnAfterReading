package de.ba.bund.burnafterreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BurnAfterReadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BurnAfterReadingApplication.class, args);
    }

}
