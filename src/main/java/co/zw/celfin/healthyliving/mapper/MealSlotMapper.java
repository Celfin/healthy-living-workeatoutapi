package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.MealSlotDto;
import co.zw.celfin.healthyliving.dto.MealSlotRequest;
import co.zw.celfin.healthyliving.entity.MealSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealSlotMapper {

    @Mapping(target = "personId", source = "person.id")
    MealSlotDto toDto(MealSlot mealSlot);

    // person is resolved and set by the service, since the mapper has no repository access
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "id", ignore = true)
    MealSlot toEntity(MealSlotRequest request);
}
