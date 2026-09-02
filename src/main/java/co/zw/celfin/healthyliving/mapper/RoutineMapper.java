package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.RoutineDto;
import co.zw.celfin.healthyliving.dto.RoutineRequest;
import co.zw.celfin.healthyliving.entity.WorkoutRoutine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

    RoutineDto toDto(WorkoutRoutine routine);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "weeklyWorkoutPlan", ignore = true)
    @Mapping(target = "sortOrder", ignore = true)
    WorkoutRoutine toEntity(RoutineRequest request);
}
