package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanRequest;
import co.zw.celfin.healthyliving.entity.WeeklyWorkoutPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoutineMapper.class)
public interface WeeklyWorkoutPlanMapper {

    @Mapping(target = "personId", source = "person.id")
    WeeklyWorkoutPlanDto toDto(WeeklyWorkoutPlan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "routines", ignore = true)
    WeeklyWorkoutPlan toEntity(WeeklyWorkoutPlanRequest request);
}
