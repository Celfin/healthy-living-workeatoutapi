package co.zw.celfin.healthyliving.service.impl;

import co.zw.celfin.healthyliving.dto.DailyOverrideDto;
import co.zw.celfin.healthyliving.dto.DailyOverrideRequest;
import co.zw.celfin.healthyliving.dto.MealIngredientRequest;
import co.zw.celfin.healthyliving.entity.DailyOverride;
import co.zw.celfin.healthyliving.entity.MealIngredient;
import co.zw.celfin.healthyliving.entity.MealSlot;
import co.zw.celfin.healthyliving.entity.Person;
import co.zw.celfin.healthyliving.exception.ResourceNotFoundException;
import co.zw.celfin.healthyliving.mapper.DailyOverrideMapper;
import co.zw.celfin.healthyliving.mapper.MealIngredientMapper;
import co.zw.celfin.healthyliving.repository.DailyOverrideRepository;
import co.zw.celfin.healthyliving.repository.MealSlotRepository;
import co.zw.celfin.healthyliving.repository.PersonRepository;
import co.zw.celfin.healthyliving.service.DailyOverrideService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyOverrideServiceImpl implements DailyOverrideService {

    private final DailyOverrideRepository dailyOverrideRepository;
    private final PersonRepository personRepository;
    private final MealSlotRepository mealSlotRepository;
    private final DailyOverrideMapper dailyOverrideMapper;
    private final MealIngredientMapper mealIngredientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DailyOverrideDto> findByPersonAndDate(Long personId, LocalDate date) {
        return dailyOverrideRepository.findByPersonIdAndDate(personId, date).stream()
                .map(dailyOverrideMapper::toDto)
                .toList();
    }

    @Override
    public DailyOverrideDto create(DailyOverrideRequest request) {
        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));
        MealSlot mealSlot = mealSlotRepository.findById(request.getMealSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Meal slot not found: " + request.getMealSlotId()));

        DailyOverride override = DailyOverride.builder()
                .person(person)
                .date(request.getDate())
                .mealSlot(mealSlot)
                .notes(request.getNotes())
                .skipMeal(request.isSkipMeal())
                .build();
        override.setIngredients(toIngredientEntities(request.getIngredients(), override));

        return dailyOverrideMapper.toDto(dailyOverrideRepository.save(override));
    }

    @Override
    public DailyOverrideDto update(Long id, DailyOverrideRequest request) {
        DailyOverride override = getOrThrow(id);

        if (!override.getPerson().getId().equals(request.getPersonId())) {
            Person person = personRepository.findById(request.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));
            override.setPerson(person);
        }
        if (!override.getMealSlot().getId().equals(request.getMealSlotId())) {
            MealSlot mealSlot = mealSlotRepository.findById(request.getMealSlotId())
                    .orElseThrow(() -> new ResourceNotFoundException("Meal slot not found: " + request.getMealSlotId()));
            override.setMealSlot(mealSlot);
        }
        override.setDate(request.getDate());
        override.setNotes(request.getNotes());
        override.setSkipMeal(request.isSkipMeal());

        override.getIngredients().clear();
        override.getIngredients().addAll(toIngredientEntities(request.getIngredients(), override));

        return dailyOverrideMapper.toDto(dailyOverrideRepository.save(override));
    }

    @Override
    public void delete(Long id) {
        if (!dailyOverrideRepository.existsById(id)) {
            throw new ResourceNotFoundException("Daily override not found: " + id);
        }
        dailyOverrideRepository.deleteById(id);
    }

    private List<MealIngredient> toIngredientEntities(List<MealIngredientRequest> requests, DailyOverride override) {
        if (CollectionUtils.isEmpty(requests)) {
            return new ArrayList<>();
        }
        List<MealIngredient> ingredients = new ArrayList<>();
        for (MealIngredientRequest request : requests) {
            MealIngredient ingredient = mealIngredientMapper.toEntity(request);
            ingredient.setDailyOverride(override);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private DailyOverride getOrThrow(Long id) {
        return dailyOverrideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Daily override not found: " + id));
    }
}
