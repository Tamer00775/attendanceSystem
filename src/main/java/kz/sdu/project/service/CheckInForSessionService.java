package kz.sdu.project.service;


import kz.sdu.project.entity.CheckInForSession;
import kz.sdu.project.repository.CheckInForSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckInForSessionService {

    private final CheckInForSessionRepo checkInForSessionRepo;

    @Autowired
    public CheckInForSessionService(CheckInForSessionRepo checkInForSessionRepo) {
        this.checkInForSessionRepo = checkInForSessionRepo;
    }

    public CheckInForSession save(CheckInForSession checkInForSession) {
        return checkInForSessionRepo.save(checkInForSession);
    }

    public Optional<CheckInForSession> findById(Integer id) {
        return checkInForSessionRepo.findById(id);
    }

    public List<CheckInForSession> findAll() {
        return checkInForSessionRepo.findAll();
    }

    public Optional<CheckInForSession> findByPersonIdAndScheduleId(Integer personId, Integer scheduleId) {
        return checkInForSessionRepo.findByPersonIdAndScheduleId(personId, scheduleId);
    }

    public CheckInForSession update(CheckInForSession checkInForSession) {
        return checkInForSessionRepo.save(checkInForSession);
    }

    public void deleteById(Integer id) {
        checkInForSessionRepo.deleteById(id);
    }
}
