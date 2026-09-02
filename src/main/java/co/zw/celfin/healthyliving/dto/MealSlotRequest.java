package co.zw.celfin.healthyliving.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Data;

@Data
public class MealSlotRequest {

    @NotNull
    private Long personId;

    @NotBlank
    private String name;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private LocalTime defaultTime;
}
