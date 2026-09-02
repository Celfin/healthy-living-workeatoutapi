package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meal_slot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(nullable = false)
    private String name; // "Lunch", "Supper", "Snack 1"

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "default_time", nullable = false)
    private LocalTime defaultTime;
}
