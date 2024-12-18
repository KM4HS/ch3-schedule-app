package com.example.schedulemanagementapp.service;

import com.example.schedulemanagementapp.Paging;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.exceptions.CustomException;
import com.example.schedulemanagementapp.exceptions.ExceptionCode;
import com.example.schedulemanagementapp.repository.ScheduleRepository;
import com.example.schedulemanagementapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // @RequiredArgsConstructor 로 대체 가능
    /*public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }*/

    /**
     * 일정 생성 서비스 단계. 요청값을 schedule에 대입
     *
     * @param password 비밀번호
     * @param contents 일정 내용
     * @param userId   작성자id
     * @return repository를 거쳐 완성된 schedule을 dto로 반환
     */
    @Override
    public ScheduleResponseDto createSchedule(String password, String contents, Long userId) {

        String userName = userRepository.findNameByUserIdOrElseThrow(userId);
        Schedule schedule = new Schedule(password, contents, userId);
        return new ScheduleResponseDto(scheduleRepository.createSchedule(schedule, userId), userName) ;
    }

    /**
     * 일정 단건 조회
     *
     * @param id 일정 식별자
     * @return repository에서 일정을 찾으면 반환
     */
    @Override
    public ScheduleResponseDto findScheduleByIdOrElseThrow(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule, userRepository.findNameByUserIdOrElseThrow(schedule.getUserId()));
    }

    /**
     * 조건별 일정 다건 조회
     *
     * @param date 날짜 조건
     * @param user 작성자 조건
     * @return 찾은 일정 배열 반환
     */
    @Override
    public List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String user) {

        List<Schedule> schedules = scheduleRepository.findAllScheduleByCond(date, user);

        return schedules.stream().map(a -> new ScheduleResponseDto(a, userRepository.findNameByUserIdOrElseThrow(a.getUserId()))).toList();
    }

    /**
     * 입력받은 값으로 페이징 객체 생성하고 repository 호출
     *
     * @param pageIndex 페이지 번호
     * @param pageSize  페이지 크기
     * @return 페이징 단위의 일정 리스트
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedulesInPage(int pageIndex, int pageSize) {

        Paging paging = new Paging(pageIndex, pageSize);
        List<Schedule> schedules = scheduleRepository.findAllSchedulesInPage(paging);

        return schedules.stream().map(a -> new ScheduleResponseDto(a, userRepository.findNameByUserIdOrElseThrow(a.getUserId()))).toList();
    }

    /**
     * 일정 수정. 내용 또는 작성자가 없을 경우, 비밀번호가 틀릴 경우 throw, 업데이트된 일정이 없을 경우 throw
     *
     * @param id       일정 식별자
     * @param password 비밀번호
     * @param contents 내용
     * @return 수정된 일정을 반환
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String password, String contents) {

        // @Valid로 검증
        /*if (contents == null) {
            throw new CustomException(ExceptionCode.INVALID_REQUEST);
        }*/

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new CustomException(ExceptionCode.PASSWORD_NOT_MATCH);
        }

        if (scheduleRepository.updateSchedule(id, contents) == 0) {
            throw new CustomException(ExceptionCode.INVALID_REQUEST);
        }

        schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule, userRepository.findNameByUserIdOrElseThrow(schedule.getUserId()));
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
            throw new CustomException(ExceptionCode.PASSWORD_NOT_MATCH);
        }

        scheduleRepository.deleteSchedule(id);
    }

    @Override
    public ScheduleResponseDto updateScheduleName(Long id, String password, String name) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new CustomException(ExceptionCode.PASSWORD_NOT_MATCH);
        }

        if(scheduleRepository.updateScheduleName(id, name) == 0) {
            throw new CustomException(ExceptionCode.INVALID_REQUEST);
        }

        schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule, userRepository.findNameByUserIdOrElseThrow(schedule.getUserId()));
    }
}
