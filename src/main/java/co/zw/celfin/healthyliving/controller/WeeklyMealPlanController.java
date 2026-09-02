package co.zw.celfin.healthyliving.controller;

import co.zw.celfin.healthyliving.dto.ApiResponse;
import co.zw.celfin.healthyliving.dto.WeeklyMealPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyMealPlanRequest;
import co.zw.celfin.healthyliving.service.WeeklyMealPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal-plans")
@RequiredArgsConstructor
@Tag(name = "Weekly Meal Plans", description = "The recurring weekly template: what to eat, per person, per day, per meal slot")
public class WeeklyMealPlanController {

    private final WeeklyMealPlanService weeklyMealPlanService;

    @GetMapping
    public ApiResponse<List<WeeklyMealPlanDto>> findByPerson(
            @RequestParam Long personId,
            @RequestParam(required = false) DayOfWeek dayOfWeek) {
        return ApiResponse.ok(weeklyMealPlanService.findByPerson(personId, dayOfWeek));
    }

    @GetMapping("/{id}")
    public ApiResponse<WeeklyMealPlanDto> findById(@PathVariable Long id) {
        return ApiResponse.ok(weeklyMealPlanService.findById(id));
    }

    @PostMapping
    public ApiResponse<WeeklyMealPlanDto> create(@Valid @RequestBody WeeklyMealPlanRequest request) {
        return ApiResponse.created(weeklyMealPlanService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<WeeklyMealPlanDto> update(@PathVariable Long id, @Valid @RequestBody WeeklyMealPlanRequest request) {
        return ApiResponse.ok(weeklyMealPlanService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        weeklyMealPlanService.delete(id);
        return ApiResponse.ok(null);
    }
}
