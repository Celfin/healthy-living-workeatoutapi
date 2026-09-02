package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "weekly_workout_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyWorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private String focus; // "Push", "Pull", "Legs", "Rest"

    @Builder.Default
    @OneToMany(mappedBy = "weeklyWorkoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<WorkoutRoutine> routines = new ArrayList<>();
}
