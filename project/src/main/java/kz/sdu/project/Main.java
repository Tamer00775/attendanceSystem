package kz.sdu.project;

import java.time.*;

public class Main {
    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("Asia/Oral");
        LocalDateTime now = LocalDateTime.now(zoneId);
        System.out.println(now);
    }
}