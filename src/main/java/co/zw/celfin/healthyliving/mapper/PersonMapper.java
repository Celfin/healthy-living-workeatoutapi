package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.PersonDto;
import co.zw.celfin.healthyliving.dto.PersonRequest;
import co.zw.celfin.healthyliving.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto toDto(Person person);

    @Mapping(target = "id", ignore = true)
    Person toEntity(PersonRequest request);
}
