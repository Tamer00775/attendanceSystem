package kz.sdu.project.repository;

import kz.sdu.project.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRoomRepo extends JpaRepository<ClassRoom, Integer> {
    @Query("select c from ClassRoom c where c.room_number = ?1")
    Optional<ClassRoom> findByRoom_number(String roomNumber);
}
