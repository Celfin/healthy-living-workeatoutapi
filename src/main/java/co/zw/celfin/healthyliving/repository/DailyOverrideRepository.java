package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.DailyOverride;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyOverrideRepository extends JpaRepository<DailyOverride, Long> {

    List<DailyOverride> findByPersonIdAndDate(Long personId, LocalDate date);
}
