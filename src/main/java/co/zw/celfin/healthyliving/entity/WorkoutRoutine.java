package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workout_routine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "weekly_workout_plan_id")
    private WeeklyWorkoutPlan weeklyWorkoutPlan;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(nullable = false, length = 500)
    private String name; // e.g. "Bench Press 4x5-8"

    @Column(length = 255)
    private String example; // gif filename / future S3 object key, e.g. "Barbell-Bench-Press.gif"
}
