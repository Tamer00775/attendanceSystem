package kz.sdu.project.resource;

import kz.sdu.project.dto.ClassRoomDTO;
import kz.sdu.project.entity.ClassRoom;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classRoom/v1")
public class ClassRoomResource {

    private final ClassRoomService classRoomService;

    @Autowired
    public ClassRoomResource(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @GetMapping
    public ResponseEntity<List<ClassRoomDTO>> all() {
        List<ClassRoom> classRooms = classRoomService.findAll();
        List<ClassRoomDTO> classRoomDTOs = classRooms.stream()
                .map(ClassRoomDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(classRoomDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> getById(@PathVariable Integer id) {
        ClassRoom classRoom = classRoomService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClassRoom with id " + id + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ClassRoomDTO.fromEntity(classRoom));
    }

    @GetMapping("/room/{roomNumber}")
    public ResponseEntity<ClassRoomDTO> getByRoomNumber(@PathVariable String roomNumber) {
        ClassRoom classRoom = classRoomService.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("ClassRoom with roomNumber " + roomNumber + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ClassRoomDTO.fromEntity(classRoom));
    }
}
