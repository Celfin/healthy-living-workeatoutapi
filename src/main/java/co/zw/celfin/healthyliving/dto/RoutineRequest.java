package co.zw.celfin.healthyliving.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoutineRequest {

    @NotBlank
    private String name;

    private String example;
}
