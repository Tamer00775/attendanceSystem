package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.Schedule;
import kz.sdu.project.repository.ScheduleRepo;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> findAll() {
        return scheduleRepo.findAll();
    }
    public List<Schedule> finByClassRoomId(Integer classRoomId) {
        return scheduleRepo.finByClassRoomId(classRoomId);
    }

    public Optional<Schedule> findById(Integer id) {
        return scheduleRepo.findById(id);
    }

    public Optional<Schedule> findBySectionName(String sectionName) {
        return scheduleRepo.findBySectionName(sectionName);
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    public void delete(Schedule schedule) {
        scheduleRepo.delete(schedule);
    }

    public void deleteById(Integer id) {
        scheduleRepo.deleteById(id);
    }
}