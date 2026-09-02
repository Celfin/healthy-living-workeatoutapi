package co.zw.celfin.healthyliving.controller;

import co.zw.celfin.healthyliving.dto.ApiResponse;
import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanRequest;
import co.zw.celfin.healthyliving.service.WeeklyWorkoutPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-plans")
@RequiredArgsConstructor
@Tag(name = "Weekly Workout Plans", description = "The gym schedule -- applies only to the adult profile")
public class WeeklyWorkoutPlanController {

    private final WeeklyWorkoutPlanService weeklyWorkoutPlanService;

    @GetMapping
    public ApiResponse<List<WeeklyWorkoutPlanDto>> findByPerson(
            @RequestParam Long personId,
            @RequestParam(required = false) DayOfWeek dayOfWeek) {
        return ApiResponse.ok(weeklyWorkoutPlanService.findByPerson(personId, dayOfWeek));
    }

    @PostMapping
    public ApiResponse<WeeklyWorkoutPlanDto> create(@Valid @RequestBody WeeklyWorkoutPlanRequest request) {
        return ApiResponse.created(weeklyWorkoutPlanService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<WeeklyWorkoutPlanDto> update(@PathVariable Long id, @Valid @RequestBody WeeklyWorkoutPlanRequest request) {
        return ApiResponse.ok(weeklyWorkoutPlanService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        weeklyWorkoutPlanService.delete(id);
        return ApiResponse.ok(null);
    }
}
