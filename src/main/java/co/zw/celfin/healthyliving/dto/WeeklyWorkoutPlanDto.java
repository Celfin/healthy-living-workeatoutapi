package co.zw.celfin.healthyliving.dto;

import java.time.DayOfWeek;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyWorkoutPlanDto {
    private Long id;
    private Long personId;
    private DayOfWeek dayOfWeek;
    private String focus;
    private List<RoutineDto> routines;
}
