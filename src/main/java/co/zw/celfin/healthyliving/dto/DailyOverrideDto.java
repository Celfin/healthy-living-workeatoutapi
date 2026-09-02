package co.zw.celfin.healthyliving.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyOverrideDto {
    private Long id;
    private Long personId;
    private LocalDate date;
    private Long mealSlotId;
    private String mealSlotName;
    private String notes;
    private boolean skipMeal;
    private List<MealIngredientDto> ingredients;
}
