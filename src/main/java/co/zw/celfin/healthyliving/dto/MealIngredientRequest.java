package co.zw.celfin.healthyliving.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MealIngredientRequest {

    @NotBlank
    private String foodName;

    @NotNull
    private Double qty;

    private Double qtyMax;

    @NotBlank
    private String unit;

    private boolean meat;

    private Integer thawHoursNeeded;
}
