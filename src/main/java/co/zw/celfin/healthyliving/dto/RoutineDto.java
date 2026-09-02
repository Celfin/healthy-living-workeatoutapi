package co.zw.celfin.healthyliving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineDto {
    private Long id;
    private String name;
    private String example;
}
