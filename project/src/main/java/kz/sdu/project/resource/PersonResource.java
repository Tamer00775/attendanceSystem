package kz.sdu.project.resource;

import kz.sdu.project.adapter.PersonAdapter;
import kz.sdu.project.dto.*;
import kz.sdu.project.entity.*;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.*;
import kz.sdu.project.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person/v1")
public class PersonResource {

    private final PersonService personService;
    private final SectionService sectionService;
    private final PersonAuthorityService personAuthorityService;
    private final ReasonForAbsenceService reasonForAbsenceService;
    private final AttendanceRecordService attendanceRecordService;
    private final AttendanceInfoService attendanceInfoService;
    private final CheckInForSessionService checkInForSessionService;

    @Autowired
    public PersonResource(PersonService personService, SectionService sectionService, PersonAuthorityService personAuthorityService, ReasonForAbsenceService reasonForAbsenceService, AttendanceRecordService attendanceRecordService, AttendanceInfoService attendanceInfoService, CheckInForSessionService checkInForSessionService) {
        this.personService = personService;
        this.sectionService = sectionService;
        this.personAuthorityService = personAuthorityService;
        this.reasonForAbsenceService = reasonForAbsenceService;
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceInfoService = attendanceInfoService;
        this.checkInForSessionService = checkInForSessionService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> all() {
        List<Person> listPerson = personService.findAll();

        return ResponseEntity.ok().body(listPerson.stream()
                .map(UserDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/current")
    public PersonDto getCurrentPerson() {
        return PersonAdapter.toPersonDto(Objects.requireNonNull(SecurityUtils.getCurrentPerson()));
    }

    @GetMapping("/roles/{name}")
    public ResponseEntity<List<RoleDTO>> roles(@PathVariable("name") String name) {
        Person person = personService.findByLogin(name)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Role with user id %s not found", name)));
        Set<Role> roles = person.getRolePerson();

        return ResponseEntity.ok().body(roles.stream()
                .map(RoleDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/auth/{id}")
    public ResponseEntity<PersonAuthorityDTO> auth(@PathVariable("id") Integer id) {
        PersonAuthority personAuthority = personAuthorityService.findByPersonId(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("PersonAuthority with user id %d not found", id)));

        return ResponseEntity.ok().body(PersonAuthorityDTO.fromEntity(personAuthority));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Set<CourseDTO>> courses(@PathVariable("id") Integer id) {
        List<Section> sections = sectionService.findByPersonId(id);
        Set<Course> courses = new HashSet<>();
        for (Section s : sections) {
            courses.add(s.getCourse_section());
        }

        return ResponseEntity.ok().body(courses.stream()
                .map(CourseDTO::fromEntity).collect(Collectors.toSet()));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<PersonInfoDTO> info(@PathVariable("id") Integer id) {
        Person person = personService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person with user id %d not found", id)));

        return ResponseEntity.ok().body(PersonInfoDTO.fromEntity(person.getPersonInfo()));
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<List<SectionDTO>> sections(@PathVariable("id") Integer id) {
        List<Section> sections = sectionService.findByPersonId(id);

        return ResponseEntity.ok().body(sections.stream()
                .map(SectionDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<List<ScheduleDTO>> schedules(@PathVariable("id") Integer id) {
        List<Section> sections = sectionService.findByPersonId(id);
        List<Schedule> schedules = sections.stream()
                .map(Section::getSchedule).collect(Collectors.toList());

        return ResponseEntity.ok().body(schedules.stream()
                .map(ScheduleDTO::fromEntity).collect(Collectors.toList()));
    }


    @GetMapping("/attRecord/{id}")
    public ResponseEntity<List<AttendanceRecordDTO>> attRecord(@PathVariable("id") Integer id) {

        List<AttendanceRecord> attendanceRecords = attendanceRecordService.findByPersonId(id);

        return ResponseEntity.ok().body(attendanceRecords.stream()
                .map(AttendanceRecordDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/attInfo/{id}")
    public ResponseEntity<List<AttendanceInfoDTO>> attInfo(@PathVariable("id") Integer id) {
        List<AttendanceInfo> attendanceInfos = attendanceInfoService.findByPersonId(id);

        return ResponseEntity.ok().body(attendanceInfos.stream()
                .map(AttendanceInfoDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/checkInForSession/{id}")
    public ResponseEntity<List<CheckInForSessionDTO>> checkInForSession(@PathVariable("id") Integer id) {
        List<CheckInForSession> checkInForSessions = checkInForSessionService.findByPersonId(id);

        return ResponseEntity.ok().body(checkInForSessions.stream()
                .map(CheckInForSessionDTO::fromEntity).collect(Collectors.toList()));
    }
}

