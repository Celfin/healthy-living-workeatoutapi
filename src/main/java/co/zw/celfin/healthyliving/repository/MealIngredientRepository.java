package co.zw.celfin.healthyliving.repository;

import co.zw.celfin.healthyliving.entity.MealIngredient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {

    List<MealIngredient> findByWeeklyMealPlan_Person_Id(Long personId);
}
