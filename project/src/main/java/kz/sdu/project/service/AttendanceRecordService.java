package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.AttendanceRecord;
import kz.sdu.project.repository.AttendanceRecordRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceRecordService {

    private final AttendanceRecordRepo attendanceRecordRepo;

    @Autowired
    public AttendanceRecordService(AttendanceRecordRepo attendanceRecordRepo) {
        this.attendanceRecordRepo = attendanceRecordRepo;
    }

    public Optional<AttendanceRecord> findByPersonIdAndScheduleIdAndCurrentWeek(Integer personId, Integer scheduleId, Integer week) {
        return attendanceRecordRepo.findByPersonIdAndScheduleIdAndCurrentWeek(personId, scheduleId, week);
    }

    public List<AttendanceRecord> findAll() {
        return attendanceRecordRepo.findAll();
    }

    public List<AttendanceRecord> findByPersonId(Integer id) {
        return attendanceRecordRepo.findByPersonId(id);
    }

    public List<AttendanceRecord> findByPersonIdAndScheduleId(Integer id, Integer id2) {
        return attendanceRecordRepo.findByPersonIdAndScheduleId(id, id2);
    }

    public void save(AttendanceRecord attendanceRecord) {
        attendanceRecordRepo.save(attendanceRecord);
    }

    public void delete(AttendanceRecord attendanceRecord) {
        attendanceRecordRepo.delete(attendanceRecord);
    }

    public void deleteById(Integer id) {
        attendanceRecordRepo.deleteById(id);
    }
}

