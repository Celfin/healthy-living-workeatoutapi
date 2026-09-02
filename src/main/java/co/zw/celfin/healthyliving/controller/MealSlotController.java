package co.zw.celfin.healthyliving.controller;

import co.zw.celfin.healthyliving.dto.ApiResponse;
import co.zw.celfin.healthyliving.dto.MealSlotDto;
import co.zw.celfin.healthyliving.dto.MealSlotRequest;
import co.zw.celfin.healthyliving.service.MealSlotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal-slots")
@RequiredArgsConstructor
@Tag(name = "Meal Slots", description = "Per-person meal slots, e.g. Lunch/Supper for an adult, plus Snack 1/2 for a baby")
public class MealSlotController {

    private final MealSlotService mealSlotService;

    @GetMapping
    public ApiResponse<List<MealSlotDto>> findByPerson(@RequestParam Long personId) {
        return ApiResponse.ok(mealSlotService.findByPerson(personId));
    }

    @PostMapping
    public ApiResponse<MealSlotDto> create(@Valid @RequestBody MealSlotRequest request) {
        return ApiResponse.created(mealSlotService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<MealSlotDto> update(@PathVariable Long id, @Valid @RequestBody MealSlotRequest request) {
        return ApiResponse.ok(mealSlotService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        mealSlotService.delete(id);
        return ApiResponse.ok(null);
    }
}
