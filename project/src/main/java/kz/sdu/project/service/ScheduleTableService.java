package kz.sdu.project.service;

import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.ScheduleTableFormatDto;
import kz.sdu.project.entity.ScheduleTable;
import kz.sdu.project.repository.ScheduleTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleTableService {

    private final ScheduleTableRepository scheduleTableRepository;

    @Autowired
    public ScheduleTableService(ScheduleTableRepository scheduleTableRepository) {
        this.scheduleTableRepository = scheduleTableRepository;
    }

    public List<ScheduleTable> findAllSchedules() {
        return scheduleTableRepository.findAll();
    }

    public List<ScheduleTable> findByScheduleIdIn(List<Integer> list) {
        return scheduleTableRepository.findByScheduleIdIn(list);
    }

    private Optional<ScheduleTable> findByScheduleId(Integer scheduleId) {
        return scheduleTableRepository.findByScheduleId(scheduleId);
    }


}
