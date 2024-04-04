package kz.sdu.project.repository;

import kz.sdu.project.entity.ScheduleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleTableRepository extends JpaRepository<ScheduleTable, Integer> {
    @Query("select s from ScheduleTable s where s.scheduleId = :schedule_id")
    Optional<ScheduleTable> findByScheduleId(@Param("schedule_id") Integer sch);

    List<ScheduleTable> findByScheduleIdIn(List<Integer> schedules_id);
}
