package kz.sdu.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "schedule_table")
@NoArgsConstructor
@Getter
@Setter
public class ScheduleTable {

    @Id
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "end_time")
    private Integer endTime;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "code")
    private String code;

    @Column(name = "type")
    private String type;

    @Override
    public String toString() {
        return "ScheduleTable{" +
                "scheduleId=" + scheduleId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", roomNumber='" + roomNumber + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleTable that = (ScheduleTable) o;
        return Objects.equals(scheduleId, that.scheduleId) &&
                Objects.equals(dayOfWeek, that.dayOfWeek) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(roomNumber, that.roomNumber) &&
                Objects.equals(code, that.code) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, dayOfWeek, startTime, endTime, roomNumber, code, type);
    }
}
