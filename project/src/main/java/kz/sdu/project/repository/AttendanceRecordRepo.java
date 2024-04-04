package kz.sdu.project.repository;

import kz.sdu.project.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepo extends JpaRepository<AttendanceRecord, Integer> {

    @Query("select a from AttendanceRecord a where a.person_att_record.id = ?1 " +
            "and a.schedule_att_record.scheduleId = ?2 " +
            "and a.currentWeek = ?3 ")
    Optional<AttendanceRecord> findByPersonIdAndScheduleIdAndCurrentWeek(Integer id,Integer id2,Integer week);


    @Query("select a from AttendanceRecord a where a.person_att_record.id = ?1 " +
            "and a.schedule_att_record.scheduleId = ?2")
    List<AttendanceRecord> findByPersonIdAndScheduleId(Integer id, Integer id2);
    @Query("select a from AttendanceRecord a where a.person_att_record.id = ?1")
    List<AttendanceRecord> findByPersonId(Integer id);
}
