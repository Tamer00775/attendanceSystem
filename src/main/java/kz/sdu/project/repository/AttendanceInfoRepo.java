package kz.sdu.project.repository;

import kz.sdu.project.entity.AttendanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceInfoRepo extends JpaRepository<AttendanceInfo, Integer> {

    @Query("select a from AttendanceInfo a where a.person_attendanceInfo.id = ?1" +
            " and a.section_att_info.sectionId = ?2")
    Optional<AttendanceInfo> findByPersonIdAndSectionId(Integer id, Integer id2);

    @Query("select a from AttendanceInfo a where a.person_attendanceInfo.login like ?1")
    List<AttendanceInfo> findAllByPersonLogin(String login);
}
