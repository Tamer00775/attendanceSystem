package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.AttendanceInfo;
import kz.sdu.project.repository.AttendanceInfoRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceInfoService {

    private final AttendanceInfoRepo attendanceInfoRepo;

    @Autowired
    public AttendanceInfoService(AttendanceInfoRepo attendanceInfoRepo) {
        this.attendanceInfoRepo = attendanceInfoRepo;
    }

    public AttendanceInfo findByPersonIdAndSectionId(Integer personId, Integer sectionId) {
        Optional<AttendanceInfo> result = attendanceInfoRepo.findByPersonIdAndSectionId(personId, sectionId);
        return result.orElse(null);
    }

    public List<AttendanceInfo> findAll() {
        return attendanceInfoRepo.findAll();
    }

    public List<AttendanceInfo> findAllByPersonLogin(String login) {
        return attendanceInfoRepo.findAllByPersonLogin(login);
    }



    public void save(AttendanceInfo attendanceInfo) {
         attendanceInfoRepo.save(attendanceInfo);
    }

    public void delete(AttendanceInfo attendanceInfo) {
        attendanceInfoRepo.delete(attendanceInfo);
    }

    public void deleteById(Integer id) {
        attendanceInfoRepo.deleteById(id);
    }
}
