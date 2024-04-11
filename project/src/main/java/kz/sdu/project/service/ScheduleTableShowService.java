package kz.sdu.project.service;

import kz.sdu.project.dto.ScheduleTableFormatDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Schedule;
import kz.sdu.project.entity.ScheduleTable;
import kz.sdu.project.entity.Section;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kz.sdu.project.utils.Constants.*;

@Service
@AllArgsConstructor
public class ScheduleTableShowService {

    private final PersonService personService;
    private final SectionService sectionService;
    private final ScheduleTableService scheduleTableService;


    public List<ScheduleTableFormatDto> schedule() {
        Person person = SecurityUtils.getCurrentPerson();
        List<Integer> scheduleIds = sectionService.findByPersonId(person.getId())
                .stream().map(Section::getSchedule)
                .map(Schedule::getScheduleId)
                .collect(Collectors.toList());

        return returnSchedule(scheduleIds);
    }

    private List<ScheduleTableFormatDto> returnSchedule(List<Integer> schedulesIds) {

        List<ScheduleTableFormatDto> schedule = generateInitialSchedule();
        List<ScheduleTable> tables = scheduleTableService.findByScheduleIdIn(schedulesIds);
        tables.forEach(table -> updateSchedule(schedule,table));
        formatTime(schedule);

        return schedule;
    }

    private List<ScheduleTableFormatDto> generateInitialSchedule() {
        List<ScheduleTableFormatDto> schedule = new ArrayList<>();
        IntStream.rangeClosed(LESSON_START_TIME, LESSON_END_TIME)
                .mapToObj(this::addScheduleTable)
                .forEach(schedule::add);
        return schedule;
    }


    private void formatTime(List<ScheduleTableFormatDto> finalSchedule) {
        finalSchedule.forEach(scheduleTableFormatDto ->
                scheduleTableFormatDto.setTime(toTimeFormat(scheduleTableFormatDto.getTime())));
    }

    private ScheduleTableFormatDto addScheduleTable(int time) {
        return ScheduleTableFormatDto.builder()
                .time(time + "")
                .Monday("")
                .Tuesday("")
                .Wednesday("")
                .Thursday("")
                .Friday("")
                .Saturday("")
                .build();
    }

    private String toTimeFormat(String givenTime) {
        try {
            int hour = Integer.parseInt(givenTime);
            if (hour < 0 || hour > 23)
                  throw new IllegalArgumentException("Hour should be in range 0 and 23");
            LocalTime time = LocalTime.of(hour, 0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.US); // Customize Locale as needed
            return time.format(formatter);
        }catch (NumberFormatException ex) {
            throw new IllegalArgumentException(String.format("Hour is not valid : %s", givenTime));
        }
    }

    private void updateSchedule(List<ScheduleTableFormatDto> schedule, ScheduleTable table) {

        int current_lesson_time = table.getStartTime(),
                till = table.getEndTime(),
                dayOfWeek = table.getDayOfWeek();

        if (current_lesson_time >= till || current_lesson_time < LESSON_START_TIME){
            throw new IllegalArgumentException(String.format("StartTime : %d and EndTime %d are bad", current_lesson_time,till));
        }

        while (current_lesson_time < till) {
            int rowIndex = current_lesson_time - LESSON_START_TIME;
            ScheduleTableFormatDto scheduleTableFormatDto =
                    schedule.get(rowIndex);
            schedule.set(rowIndex,changeByDay(scheduleTableFormatDto,dayOfWeek,table));
            current_lesson_time++;
        }
    }

    private ScheduleTableFormatDto changeByDay(ScheduleTableFormatDto scheduleTableFormatDto, int dayOfWeek, ScheduleTable table) {
        String code = table.getCode(),
                roomNumber = table.getRoomNumber(),
                type = table.getType();
        String info = code + "<br>" + roomNumber + "<br>" + type + "<br>";
        switch (dayOfWeek) {
            case MONDAY:
                scheduleTableFormatDto.setMonday(info);
                break;
            case TUESDAY:
                scheduleTableFormatDto.setTuesday(info);
                break;
            case WEDNESDAY:
                scheduleTableFormatDto.setWednesday(info);
                break;
            case THURSDAY:
                scheduleTableFormatDto.setThursday(info);
                break;
            case FRIDAY:
                scheduleTableFormatDto.setFriday(info);
                break;
            case SATURDAY:
                scheduleTableFormatDto.setSaturday(info);
                break;
            default:
                throw new EntityNotFoundException("DayOfWeek " + dayOfWeek + " is out of range.");
        }
        return scheduleTableFormatDto;
    }


}
