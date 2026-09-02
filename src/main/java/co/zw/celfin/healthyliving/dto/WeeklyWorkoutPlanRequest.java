package co.zw.celfin.healthyliving.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;
import lombok.Data;

@Data
public class WeeklyWorkoutPlanRequest {

    @NotNull
    private Long personId;

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotBlank
    private String focus;

    private List<@Valid RoutineRequest> routines;
}
