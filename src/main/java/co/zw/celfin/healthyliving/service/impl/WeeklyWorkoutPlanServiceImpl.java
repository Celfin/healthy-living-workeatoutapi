package co.zw.celfin.healthyliving.service.impl;

import co.zw.celfin.healthyliving.dto.RoutineRequest;
import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyWorkoutPlanRequest;
import co.zw.celfin.healthyliving.entity.Person;
import co.zw.celfin.healthyliving.entity.WeeklyWorkoutPlan;
import co.zw.celfin.healthyliving.entity.WorkoutRoutine;
import co.zw.celfin.healthyliving.exception.ResourceNotFoundException;
import co.zw.celfin.healthyliving.mapper.RoutineMapper;
import co.zw.celfin.healthyliving.mapper.WeeklyWorkoutPlanMapper;
import co.zw.celfin.healthyliving.repository.PersonRepository;
import co.zw.celfin.healthyliving.repository.WeeklyWorkoutPlanRepository;
import co.zw.celfin.healthyliving.service.WeeklyWorkoutPlanService;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyWorkoutPlanServiceImpl implements WeeklyWorkoutPlanService {

    private final WeeklyWorkoutPlanRepository weeklyWorkoutPlanRepository;
    private final PersonRepository personRepository;
    private final WeeklyWorkoutPlanMapper weeklyWorkoutPlanMapper;
    private final RoutineMapper routineMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyWorkoutPlanDto> findByPerson(Long personId, DayOfWeek dayOfWeek) {
        List<WeeklyWorkoutPlan> plans = dayOfWeek == null
                ? weeklyWorkoutPlanRepository.findByPersonId(personId)
                : weeklyWorkoutPlanRepository.findByPersonIdAndDayOfWeek(personId, dayOfWeek);
        return plans.stream().map(weeklyWorkoutPlanMapper::toDto).toList();
    }

    @Override
    public WeeklyWorkoutPlanDto create(WeeklyWorkoutPlanRequest request) {
        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));

        WeeklyWorkoutPlan plan = weeklyWorkoutPlanMapper.toEntity(request);
        plan.setPerson(person);
        plan.setRoutines(toRoutineEntities(request.getRoutines(), plan));
        return weeklyWorkoutPlanMapper.toDto(weeklyWorkoutPlanRepository.save(plan));
    }

    @Override
    public WeeklyWorkoutPlanDto update(Long id, WeeklyWorkoutPlanRequest request) {
        WeeklyWorkoutPlan plan = weeklyWorkoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weekly workout plan not found: " + id));

        if (!plan.getPerson().getId().equals(request.getPersonId())) {
            Person person = personRepository.findById(request.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));
            plan.setPerson(person);
        }
        plan.setDayOfWeek(request.getDayOfWeek());
        plan.setFocus(request.getFocus());

        // orphanRemoval on the collection means replacing its contents deletes the old rows
        plan.getRoutines().clear();
        plan.getRoutines().addAll(toRoutineEntities(request.getRoutines(), plan));

        return weeklyWorkoutPlanMapper.toDto(weeklyWorkoutPlanRepository.save(plan));
    }

    @Override
    public void delete(Long id) {
        if (!weeklyWorkoutPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Weekly workout plan not found: " + id);
        }
        weeklyWorkoutPlanRepository.deleteById(id);
    }

    private List<WorkoutRoutine> toRoutineEntities(List<RoutineRequest> requests, WeeklyWorkoutPlan plan) {
        if (CollectionUtils.isEmpty(requests)) {
            return new ArrayList<>();
        }
        List<WorkoutRoutine> routines = new ArrayList<>();
        int sortOrder = 0;
        for (RoutineRequest request : requests) {
            WorkoutRoutine routine = routineMapper.toEntity(request);
            routine.setWeeklyWorkoutPlan(plan);
            routine.setSortOrder(sortOrder++);
            routines.add(routine);
        }
        return routines;
    }
}
