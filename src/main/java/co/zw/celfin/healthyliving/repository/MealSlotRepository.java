package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.MealSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealSlotRepository extends JpaRepository<MealSlot, Long> {

    List<MealSlot> findByPersonIdOrderBySortOrderAsc(Long personId);
}
