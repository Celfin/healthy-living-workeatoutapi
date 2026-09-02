package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.WeeklyMealPlan;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyMealPlanRepository extends JpaRepository<WeeklyMealPlan, Long> {

    List<WeeklyMealPlan> findByPersonId(Long personId);

    List<WeeklyMealPlan> findByPersonIdAndDayOfWeek(Long personId, DayOfWeek dayOfWeek);
}
