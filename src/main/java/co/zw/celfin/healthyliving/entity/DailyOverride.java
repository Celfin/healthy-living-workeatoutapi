package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "daily_override")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meal_slot_id")
    private MealSlot mealSlot;

    @Column(length = 500)
    private String notes;

    @Column(name = "skip_meal", nullable = false)
    private boolean skipMeal;

    @Builder.Default
    @OneToMany(mappedBy = "dailyOverride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealIngredient> ingredients = new ArrayList<>();
}
