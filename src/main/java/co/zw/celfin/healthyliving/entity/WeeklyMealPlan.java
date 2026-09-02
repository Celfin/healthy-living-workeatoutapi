package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "weekly_meal_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyMealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @ManyToOne(optional = false)
    @JoinColumn(name = "meal_slot_id")
    private MealSlot mealSlot;

    @Column(length = 500)
    private String notes;

    @Builder.Default
    @OneToMany(mappedBy = "weeklyMealPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealIngredient> ingredients = new ArrayList<>();

    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @PrePersist
    @PreUpdate
    void touch() {
        this.lastModified = Instant.now();
    }
}
