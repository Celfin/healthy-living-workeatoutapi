package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.GroceryItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {

    List<GroceryItem> findByMonthKeyOrderByFoodNameAsc(String monthKey);

    Optional<GroceryItem> findByMonthKeyAndFoodNameAndUnit(String monthKey, String foodName, String unit);

    void deleteByMonthKey(String monthKey);
}
