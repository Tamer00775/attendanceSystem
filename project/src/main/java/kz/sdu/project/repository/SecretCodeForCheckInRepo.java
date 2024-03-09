package kz.sdu.project.repository;

import kz.sdu.project.entity.SecretCodeForCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SecretCodeForCheckInRepo extends JpaRepository<SecretCodeForCheckIn, Integer> {

    @Query("select s from SecretCodeForCheckIn s where s.schedule_checkin.scheduleId = ?1")
    Optional<SecretCodeForCheckIn> findByScheduleId(Integer id);
}
