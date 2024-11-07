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
 * <li>description    : 일정 서비스 레이어 구현 클래스
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

    /**
     * 일정 생성 서비스 단계. 요청값을 schedule에 대입
     *
     * @param password 비밀번호
     * @param contents 일정 내용
     * @param user   작성자
     * @return repository를 거쳐 완성된 schedule을 dto로 반환
     */
    @Override
    public ScheduleResponseDto createSchedule(String password, String contents, String user) {

        Schedule schedule = new Schedule(password, contents, user);

        return scheduleRepository.createSchedule(schedule);
    }

    /**
     * 일정 단건 조회
     *
     * @param id 일정 식별자
     * @return repository에서 일정을 찾으면 반환
     */
    @Override
    public ScheduleResponseDto findScheduleByIdOrElseThrow(Long id) {

        return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));
    }

    /**
     * 조건별 일정 다건 조회
     *
     * @param date   날짜 조건
     * @param user 작성자 조건
     * @return 찾은 일정 배열 반환
     */
    @Override
    public List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String user) {

        return scheduleRepository.findAllScheduleByCond(date, user);
    }

    /**
     * 일정 수정. 내용 또는 작성자가 없을 경우, 비밀번호가 틀릴 경우 throw, 업데이트된 일정이 없을 경우 throw
     *
     * @param id       일정 식별자
     * @param password 비밀번호
     * @param contents 내용
     * @param user   작성자
     * @return 수정된 일정을 반환
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String password, String contents, String user) {

        if (contents == null || user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contents and user are required");
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        if (scheduleRepository.updateSchedule(id, contents, user) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));
    }

    /**
     * 일정 삭제. 비밀번호 틀릴 경우 throw
     *
     * @param id       일정 식별자
     * @param password 비밀번호
     */
    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        scheduleRepository.deleteSchedule(id);
    }
}
