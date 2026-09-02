package co.zw.celfin.healthyliving.service.impl;

import co.zw.celfin.healthyliving.dto.MealIngredientRequest;
import co.zw.celfin.healthyliving.dto.WeeklyMealPlanDto;
import co.zw.celfin.healthyliving.dto.WeeklyMealPlanRequest;
import co.zw.celfin.healthyliving.entity.MealIngredient;
import co.zw.celfin.healthyliving.entity.MealSlot;
import co.zw.celfin.healthyliving.entity.Person;
import co.zw.celfin.healthyliving.entity.WeeklyMealPlan;
import co.zw.celfin.healthyliving.exception.ResourceNotFoundException;
import co.zw.celfin.healthyliving.mapper.MealIngredientMapper;
import co.zw.celfin.healthyliving.mapper.WeeklyMealPlanMapper;
import co.zw.celfin.healthyliving.repository.MealSlotRepository;
import co.zw.celfin.healthyliving.repository.PersonRepository;
import co.zw.celfin.healthyliving.repository.WeeklyMealPlanRepository;
import co.zw.celfin.healthyliving.service.WeeklyMealPlanService;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyMealPlanServiceImpl implements WeeklyMealPlanService {

    private final WeeklyMealPlanRepository weeklyMealPlanRepository;
    private final PersonRepository personRepository;
    private final MealSlotRepository mealSlotRepository;
    private final WeeklyMealPlanMapper weeklyMealPlanMapper;
    private final MealIngredientMapper mealIngredientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyMealPlanDto> findByPerson(Long personId, DayOfWeek dayOfWeek) {
        List<WeeklyMealPlan> plans = dayOfWeek == null
                ? weeklyMealPlanRepository.findByPersonId(personId)
                : weeklyMealPlanRepository.findByPersonIdAndDayOfWeek(personId, dayOfWeek);
        return plans.stream().map(weeklyMealPlanMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WeeklyMealPlanDto findById(Long id) {
        return weeklyMealPlanMapper.toDto(getOrThrow(id));
    }

    @Override
    public WeeklyMealPlanDto create(WeeklyMealPlanRequest request) {
        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));
        MealSlot mealSlot = mealSlotRepository.findById(request.getMealSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Meal slot not found: " + request.getMealSlotId()));

        WeeklyMealPlan plan = WeeklyMealPlan.builder()
                .person(person)
                .dayOfWeek(request.getDayOfWeek())
                .mealSlot(mealSlot)
                .notes(request.getNotes())
                .build();
        plan.setIngredients(toIngredientEntities(request.getIngredients(), plan));

        return weeklyMealPlanMapper.toDto(weeklyMealPlanRepository.save(plan));
    }

    @Override
    public WeeklyMealPlanDto update(Long id, WeeklyMealPlanRequest request) {
        WeeklyMealPlan plan = getOrThrow(id);

        if (!plan.getPerson().getId().equals(request.getPersonId())) {
            Person person = personRepository.findById(request.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));
            plan.setPerson(person);
        }
        if (!plan.getMealSlot().getId().equals(request.getMealSlotId())) {
            MealSlot mealSlot = mealSlotRepository.findById(request.getMealSlotId())
                    .orElseThrow(() -> new ResourceNotFoundException("Meal slot not found: " + request.getMealSlotId()));
            plan.setMealSlot(mealSlot);
        }
        plan.setDayOfWeek(request.getDayOfWeek());
        plan.setNotes(request.getNotes());

        // orphanRemoval on the collection means replacing its contents deletes the old rows
        plan.getIngredients().clear();
        plan.getIngredients().addAll(toIngredientEntities(request.getIngredients(), plan));

        return weeklyMealPlanMapper.toDto(weeklyMealPlanRepository.save(plan));
    }

    @Override
    public void delete(Long id) {
        if (!weeklyMealPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Weekly meal plan not found: " + id);
        }
        weeklyMealPlanRepository.deleteById(id);
    }

    private List<MealIngredient> toIngredientEntities(List<MealIngredientRequest> requests, WeeklyMealPlan plan) {
        List<MealIngredient> ingredients = new ArrayList<>();
        for (MealIngredientRequest request : requests) {
            MealIngredient ingredient = mealIngredientMapper.toEntity(request);
            ingredient.setWeeklyMealPlan(plan);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private WeeklyMealPlan getOrThrow(Long id) {
        return weeklyMealPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weekly meal plan not found: " + id));
    }
}
