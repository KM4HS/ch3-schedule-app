package com.example.schedulemanagementapp.service;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.service
 * <li>fileName       : ScheduleServiceImpl
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(String password, String contents, String writer) {

        Schedule schedule = new Schedule(password, contents, writer);

        return scheduleRepository.createSchedule(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleByIdOrElseThrow(Long id) {

        return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));
    }

    @Override
    public List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String writer) {

        return scheduleRepository.findAllScheduleByCond(date, writer);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String password, String contents, String writer) {

        if(contents == null || writer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contents and writer are required");
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if(!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        if(scheduleRepository.updateSchedule(id, contents, writer) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if(!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        scheduleRepository.deleteSchedule(id);
    }
}
