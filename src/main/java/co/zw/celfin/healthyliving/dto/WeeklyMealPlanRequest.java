package co.zw.celfin.healthyliving.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;
import lombok.Data;

@Data
public class WeeklyMealPlanRequest {

    @NotNull
    private Long personId;

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    private Long mealSlotId;

    private String notes;

    @NotEmpty
    private List<@Valid MealIngredientRequest> ingredients;
}
