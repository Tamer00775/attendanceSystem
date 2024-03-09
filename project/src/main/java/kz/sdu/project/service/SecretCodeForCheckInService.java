package kz.sdu.project.service;


import kz.sdu.project.entity.SecretCodeForCheckIn;
import kz.sdu.project.repository.SecretCodeForCheckInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecretCodeForCheckInService {

    private final SecretCodeForCheckInRepo secretCodeForCheckInRepo;

    @Autowired
    public SecretCodeForCheckInService(SecretCodeForCheckInRepo secretCodeForCheckInRepo) {
        this.secretCodeForCheckInRepo = secretCodeForCheckInRepo;
    }

    public SecretCodeForCheckIn save(SecretCodeForCheckIn secretCode) {
        return secretCodeForCheckInRepo.save(secretCode);
    }

    public Optional<SecretCodeForCheckIn> findById(Integer id) {
        return secretCodeForCheckInRepo.findById(id);
    }

    public List<SecretCodeForCheckIn> findAll() {
        return secretCodeForCheckInRepo.findAll();
    }

    public Optional<SecretCodeForCheckIn> findByScheduleId(Integer scheduleId) {
        return secretCodeForCheckInRepo.findByScheduleId(scheduleId);
    }

    public SecretCodeForCheckIn update(SecretCodeForCheckIn secretCode) {
        return secretCodeForCheckInRepo.save(secretCode);
    }

    public void deleteById(Integer id) {
        secretCodeForCheckInRepo.deleteById(id);
    }
}
