package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.ClassRoom;
import kz.sdu.project.repository.ClassRoomRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {

    private final ClassRoomRepo classRoomRepo;

    @Autowired
    public ClassRoomService(ClassRoomRepo classRoomRepo) {
        this.classRoomRepo = classRoomRepo;
    }

    public Optional<ClassRoom> findByRoomNumber(String roomNumber) {
        return classRoomRepo.findByRoom_number(roomNumber);
    }

    public Optional<ClassRoom> findById(Integer id) {
        return classRoomRepo.findById(id);
    }

    public List<ClassRoom> findAll() {
        return classRoomRepo.findAll();
    }

    public ClassRoom save(ClassRoom classRoom) {
        return classRoomRepo.save(classRoom);
    }

    public void delete(ClassRoom classRoom) {
        classRoomRepo.delete(classRoom);
    }

    public void deleteById(Integer id) {
        classRoomRepo.deleteById(id);
    }
}
