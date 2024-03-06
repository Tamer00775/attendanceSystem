package kz.sdu.project.repository;

import kz.sdu.project.entity.CheckInForSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckInForSessionRepo extends JpaRepository<CheckInForSession, Integer> {
    @Query("select c from CheckInForSession c where c.person.id = ?1 and c.schedule.scheduleId = ?2")
    Optional<CheckInForSession> findByPersonIdAndScheduleId(Integer id, Integer id2);
}
