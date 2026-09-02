package co.zw.celfin.healthyliving.service;

import co.zw.celfin.healthyliving.dto.MealSlotDto;
import co.zw.celfin.healthyliving.dto.MealSlotRequest;
import java.util.List;

public interface MealSlotService {

    List<MealSlotDto> findByPerson(Long personId);

    MealSlotDto create(MealSlotRequest request);

    MealSlotDto update(Long id, MealSlotRequest request);

    void delete(Long id);
}
