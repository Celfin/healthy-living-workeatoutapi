package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meal_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // exactly one of these two is set: a weekly template ingredient, or a one-off override ingredient
    @ManyToOne
    @JoinColumn(name = "weekly_meal_plan_id")
    private WeeklyMealPlan weeklyMealPlan;

    @ManyToOne
    @JoinColumn(name = "daily_override_id")
    private DailyOverride dailyOverride;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Double qty;

    @Column(name = "qty_max")
    private Double qtyMax;

    @Column(nullable = false)
    private String unit; // "g", "ml", "unit"

    @Column(name = "is_meat", nullable = false)
    private boolean meat;

    @Column(name = "thaw_hours_needed")
    private Integer thawHoursNeeded;
}
