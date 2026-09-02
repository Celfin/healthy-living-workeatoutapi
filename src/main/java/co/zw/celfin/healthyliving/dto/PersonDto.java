package co.zw.celfin.healthyliving.dto;

import co.zw.celfin.healthyliving.entity.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private Long id;
    private String name;
    private PersonType type;
}
