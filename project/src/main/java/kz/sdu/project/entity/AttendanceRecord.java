package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "attendance_record")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Integer recordId;

    @Column(name = "total_present_hours")
    private Integer total_present_hours;

    @Column(name = "total_hours")
    private Integer total_hours;

    @Column(name = "current_week")
    private Integer currentWeek;

    @Column(name = "record_type")
    private String record_type;

    @Column(name = "is_with_reason")
    private Boolean is_with_reason;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_att_record;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    private Schedule schedule_att_record;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendanceRecord that = (AttendanceRecord) o;

        return recordId != null ? recordId.equals(that.recordId) : that.recordId == null;
    }

    @Override
    public int hashCode() {
        return recordId != null ? recordId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "recordId=" + recordId +
                ", total_present_hours=" + total_present_hours +
                ", total_hours=" + total_hours +
                ", currentWeek=" + currentWeek +
                ", record_type='" + record_type + '\'' +
                ", is_with_reason=" + is_with_reason +
                '}';
    }
}
