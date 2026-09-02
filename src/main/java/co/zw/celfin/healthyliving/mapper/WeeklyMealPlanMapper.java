package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.WeeklyMealPlanDto;
import co.zw.celfin.healthyliving.entity.WeeklyMealPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MealIngredientMapper.class)
public interface WeeklyMealPlanMapper {

    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "mealSlotId", source = "mealSlot.id")
    @Mapping(target = "mealSlotName", source = "mealSlot.name")
    WeeklyMealPlanDto toDto(WeeklyMealPlan plan);
}
