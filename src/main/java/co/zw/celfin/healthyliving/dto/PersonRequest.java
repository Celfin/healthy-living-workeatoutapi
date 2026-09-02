package co.zw.celfin.healthyliving.dto;

import co.zw.celfin.healthyliving.entity.PersonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonRequest {

    @NotBlank
    private String name;

    @NotNull
    private PersonType type;
}
