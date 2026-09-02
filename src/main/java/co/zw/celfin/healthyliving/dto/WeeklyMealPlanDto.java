package co.zw.celfin.healthyliving.dto;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyMealPlanDto {
    private Long id;
    private Long personId;
    private DayOfWeek dayOfWeek;
    private Long mealSlotId;
    private String mealSlotName;
    private String notes;
    private List<MealIngredientDto> ingredients;
    private Instant lastModified;
}
