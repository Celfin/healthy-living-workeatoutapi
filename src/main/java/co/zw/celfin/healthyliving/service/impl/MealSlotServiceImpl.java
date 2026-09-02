package co.zw.celfin.healthyliving.service.impl;

import co.zw.celfin.healthyliving.dto.MealSlotDto;
import co.zw.celfin.healthyliving.dto.MealSlotRequest;
import co.zw.celfin.healthyliving.entity.MealSlot;
import co.zw.celfin.healthyliving.entity.Person;
import co.zw.celfin.healthyliving.exception.ResourceNotFoundException;
import co.zw.celfin.healthyliving.mapper.MealSlotMapper;
import co.zw.celfin.healthyliving.repository.MealSlotRepository;
import co.zw.celfin.healthyliving.repository.PersonRepository;
import co.zw.celfin.healthyliving.service.MealSlotService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealSlotServiceImpl implements MealSlotService {

    private final MealSlotRepository mealSlotRepository;
    private final PersonRepository personRepository;
    private final MealSlotMapper mealSlotMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MealSlotDto> findByPerson(Long personId) {
        return mealSlotRepository.findByPersonIdOrderBySortOrderAsc(personId).stream()
                .map(mealSlotMapper::toDto)
                .toList();
    }

    @Override
    public MealSlotDto create(MealSlotRequest request) {
        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));

        MealSlot mealSlot = mealSlotMapper.toEntity(request);
        mealSlot.setPerson(person);
        return mealSlotMapper.toDto(mealSlotRepository.save(mealSlot));
    }

    @Override
    public MealSlotDto update(Long id, MealSlotRequest request) {
        MealSlot mealSlot = mealSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal slot not found: " + id));

        if (!mealSlot.getPerson().getId().equals(request.getPersonId())) {
            Person person = personRepository.findById(request.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + request.getPersonId()));
            mealSlot.setPerson(person);
        }
        mealSlot.setName(request.getName());
        mealSlot.setSortOrder(request.getSortOrder());
        mealSlot.setDefaultTime(request.getDefaultTime());
        return mealSlotMapper.toDto(mealSlotRepository.save(mealSlot));
    }

    @Override
    public void delete(Long id) {
        if (!mealSlotRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meal slot not found: " + id);
        }
        mealSlotRepository.deleteById(id);
    }
}
