package kz.sdu.project;

import java.time.*;

public class Main {
    public static void main(String[] args) {
        Clock utcClock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));

        // Get LocalDateTime using the UTC clock
        LocalDateTime utcDateTime = LocalDateTime.now(utcClock);

        System.out.println(utcDateTime);
    }
}