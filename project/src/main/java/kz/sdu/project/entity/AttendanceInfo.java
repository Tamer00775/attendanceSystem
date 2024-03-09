package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "attendance_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_info_id")
    private Integer attendance_info_id;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @Column(name = "full_time", nullable = false)
    private Integer full_time;

    @Column(name = "reason_time", nullable = false)
    private Integer reason_time;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_attendanceInfo;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section_att_info;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttendanceInfo)) return false;
        AttendanceInfo that = (AttendanceInfo) o;
        return Objects.equals(attendance_info_id, that.attendance_info_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendance_info_id);
    }

    @Override
    public String toString() {
        return "AttendanceInfo{" +
                "attendanceInfoId=" + attendance_info_id +
                ", percent=" + percent +
                ", fullTime=" + full_time +
                ", reasonTime=" + reason_time +
                '}';
    }

}
