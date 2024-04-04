package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;


    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private Integer startTime;

    @Column(name = "total_hours")
    private Integer totalHours;

    @OneToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section_schedule;

    @ManyToOne
    @JoinColumn(name = "class_room_id", referencedColumnName = "class_room_id")
    private ClassRoom classRoom_schedule;

    @OneToMany(mappedBy = "schedule_att_record", cascade = CascadeType.ALL)
    private Set<AttendanceRecord> attendanceRecords;

    @OneToMany(mappedBy = "schedule_checkin", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SecretCodeForCheckIn> secretCodesForCheckIn;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CheckInForSession> checkInsForSession = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule that = (Schedule) o;
        return Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime='" + startTime + '\'' +
                ", totalHours=" + totalHours +
                ", section_schedule=" + (section_schedule != null ? section_schedule.getSectionId() : "null") +
                ", classRoom_schedule=" + (classRoom_schedule != null ? classRoom_schedule.getClass_room_id() : "null") +
                '}';
    }


}