package co.zw.celfin.healthyliving.service;

import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanRequest;
import java.time.DayOfWeek;
import java.util.List;

public interface WeeklyWorkoutPlanService {

    List<WeeklyWorkoutPlanDto> findByPerson(Long personId, DayOfWeek dayOfWeek);

    WeeklyWorkoutPlanDto create(WeeklyWorkoutPlanRequest request);

    WeeklyWorkoutPlanDto update(Long id, WeeklyWorkoutPlanRequest request);

    void delete(Long id);
}
