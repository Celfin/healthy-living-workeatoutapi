package co.zw.celfin.healthyliving.service;

import co.zw.celfin.healthyliving.dto.WeeklyMealPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyMealPlanRequest;
import java.time.DayOfWeek;
import java.util.List;

public interface WeeklyMealPlanService {

    List<WeeklyMealPlanDto> findByPerson(Long personId, DayOfWeek dayOfWeek);

    WeeklyMealPlanDto findById(Long id);

    WeeklyMealPlanDto create(WeeklyMealPlanRequest request);

    WeeklyMealPlanDto update(Long id, WeeklyMealPlanRequest request);

    void delete(Long id);
}
