package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.WeeklyWorkoutPlan;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyWorkoutPlanRepository extends JpaRepository<WeeklyWorkoutPlan, Long> {

    List<WeeklyWorkoutPlan> findByPersonId(Long personId);

    List<WeeklyWorkoutPlan> findByPersonIdAndDayOfWeek(Long personId, DayOfWeek dayOfWeek);
}
