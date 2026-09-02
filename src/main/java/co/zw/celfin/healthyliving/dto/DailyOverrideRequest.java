package co.zw.celfin.healthyliving.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class DailyOverrideRequest {

    @NotNull
    private Long personId;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long mealSlotId;

    private String notes;

    private boolean skipMeal;

    private List<@Valid MealIngredientRequest> ingredients;
}
