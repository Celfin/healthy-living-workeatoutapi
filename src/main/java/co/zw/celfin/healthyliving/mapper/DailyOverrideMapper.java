package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.DailyOverrideDto;
import co.zw.celfin.healthyliving.entity.DailyOverride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MealIngredientMapper.class)
public interface DailyOverrideMapper {

    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "mealSlotId", source = "mealSlot.id")
    @Mapping(target = "mealSlotName", source = "mealSlot.name")
    DailyOverrideDto toDto(DailyOverride override);
}
