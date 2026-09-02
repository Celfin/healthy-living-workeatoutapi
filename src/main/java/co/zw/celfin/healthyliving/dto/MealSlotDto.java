package co.zw.celfin.healthyliving.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealSlotDto {
    private Long id;
    private Long personId;
    private String name;
    private Integer sortOrder;
    private LocalTime defaultTime;
}
