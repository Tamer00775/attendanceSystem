package kz.sdu.project.repository;

import kz.sdu.project.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {

    @Query("select s from Schedule s where s.section_schedule.name like ?1")
    Optional<Schedule> findBySectionName(String sectionName);
}
