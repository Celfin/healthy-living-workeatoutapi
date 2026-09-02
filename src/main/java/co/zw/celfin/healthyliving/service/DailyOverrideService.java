package co.zw.celfin.healthyliving.service;

import co.zw.celfin.healthyliving.dto.DailyOverrideDto;
import co.zw.celfin.healthyliving.dto.DailyOverrideRequest;
import java.time.LocalDate;
import java.util.List;

public interface DailyOverrideService {

    List<DailyOverrideDto> findByPersonAndDate(Long personId, LocalDate date);

    DailyOverrideDto create(DailyOverrideRequest request);

    DailyOverrideDto update(Long id, DailyOverrideRequest request);

    void delete(Long id);
}
