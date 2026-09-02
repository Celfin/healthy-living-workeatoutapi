package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.MealIngredientDto;
import co.zw.celfin.healthyliving.dto.MealIngredientRequest;
import co.zw.celfin.healthyliving.entity.MealIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealIngredientMapper {

    MealIngredientDto toDto(MealIngredient ingredient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "weeklyMealPlan", ignore = true)
    @Mapping(target = "dailyOverride", ignore = true)
    MealIngredient toEntity(MealIngredientRequest request);
}
