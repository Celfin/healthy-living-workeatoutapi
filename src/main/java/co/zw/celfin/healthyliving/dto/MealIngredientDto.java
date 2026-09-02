package co.zw.celfin.healthyliving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealIngredientDto {
    private Long id;
    private String foodName;
    private Double qty;
    private Double qtyMax;
    private String unit;
    private boolean meat;
    private Integer thawHoursNeeded;
}
