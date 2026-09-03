package co.zw.celfin.healthyliving.config;

import co.zw.celfin.healthyliving.entity.MealIngredient;
import co.zw.celfin.healthyliving.entity.MealSlot;
import co.zw.celfin.healthyliving.entity.Person;
import co.zw.celfin.healthyliving.entity.PersonType;
import co.zw.celfin.healthyliving.entity.WeeklyMealPlan;
import co.zw.celfin.healthyliving.entity.WeeklyWorkoutPlan;
import co.zw.celfin.healthyliving.entity.WorkoutRoutine;
import co.zw.celfin.healthyliving.repository.MealSlotRepository;
import co.zw.celfin.healthyliving.repository.PersonRepository;
import co.zw.celfin.healthyliving.repository.WeeklyMealPlanRepository;
import co.zw.celfin.healthyliving.repository.WeeklyWorkoutPlanRepository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads the 5-day gym and meal plan on first startup, from
 * 5_day_gym_and_meal_plan.xlsx ("Weekly Plan" and "Workout Summary" sheets).
 *
 * Seeding is skipped once any person exists, so this is a no-op on every restart
 * after the first and never overwrites edits made through the API.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    /** Hours of fridge thawing each cut needs before it can be cooked. */
    private static final int THAW_HOURS_CHICKEN = 12;
    private static final int THAW_HOURS_BEEF = 24;
    private static final int THAW_HOURS_FISH = 4;

    private final PersonRepository personRepository;
    private final MealSlotRepository mealSlotRepository;
    private final WeeklyMealPlanRepository weeklyMealPlanRepository;
    private final WeeklyWorkoutPlanRepository weeklyWorkoutPlanRepository;

    @Override
    @Transactional
    public void run(String... args) {
        long existing = personRepository.count();
        if (existing > 0) {
            log.info("Seed data skipped: {} person record(s) already present", existing);
            return;
        }

        Person kuziva = personRepository.save(Person.builder()
                .name("Kuziva")
                .type(PersonType.ADULT)
                .build());

        MealSlot lunch = mealSlotRepository.save(MealSlot.builder()
                .person(kuziva)
                .name("Lunch")
                .sortOrder(1)
                .defaultTime(LocalTime.of(12, 30))
                .build());
        MealSlot supper = mealSlotRepository.save(MealSlot.builder()
                .person(kuziva)
                .name("Supper")
                .sortOrder(2)
                .defaultTime(LocalTime.of(19, 0))
                .build());

        seedMealPlans(kuziva, lunch, supper);
        seedWorkoutPlans(kuziva);

        log.info("Seeded {} meal plan(s) and {} workout plan(s) for {}",
                weeklyMealPlanRepository.count(), weeklyWorkoutPlanRepository.count(), kuziva.getName());
    }

    private void seedMealPlans(Person person, MealSlot lunch, MealSlot supper) {
        weeklyMealPlanRepository.saveAll(List.of(

                mealPlan(person, DayOfWeek.MONDAY, lunch, null,
                        units("Eggs", 4), grams("Oats", 80), millilitres("Milk", 300),
                        grams("Banana", 120), grams("Avocado", 70)),
                mealPlan(person, DayOfWeek.MONDAY, supper, null,
                        meat("Chicken", 250, 300.0, THAW_HOURS_CHICKEN), grams("Cooked Rice", 250),
                        grams("Covo/Spinach", 200), grams("Salad", 150), millilitres("Olive Oil", 10)),

                mealPlan(person, DayOfWeek.TUESDAY, lunch, null,
                        units("Eggs", 3), grams("Plain/Greek Yoghurt", 300), grams("Oats", 70),
                        grams("Apple/Orange", 150), grams("Nuts", 25)),
                mealPlan(person, DayOfWeek.TUESDAY, supper, null,
                        meat("Lean Beef", 250, null, THAW_HOURS_BEEF), grams("Potatoes", 300),
                        grams("Mixed Vegetables", 250), grams("Avocado", 70)),

                mealPlan(person, DayOfWeek.WEDNESDAY, lunch, null,
                        units("Eggs", 4), grams("Whole-grain Bread", 100), grams("Avocado", 70),
                        grams("Banana", 120), grams("Yoghurt", 200)),
                mealPlan(person, DayOfWeek.WEDNESDAY, supper, null,
                        meat("Chicken", 250, 300.0, THAW_HOURS_CHICKEN), grams("Rice", 250),
                        grams("Beans", 120), grams("Covo/Spinach", 200)),

                mealPlan(person, DayOfWeek.THURSDAY, lunch, null,
                        grams("Oats", 80), millilitres("Milk", 300), units("Eggs", 3),
                        grams("Peanut Butter", 20), grams("Banana", 120)),
                mealPlan(person, DayOfWeek.THURSDAY, supper, null,
                        meat("Fish", 250, 300.0, THAW_HOURS_FISH), grams("Sweet Potato", 300),
                        grams("Vegetables", 250), grams("Avocado", 70)),

                mealPlan(person, DayOfWeek.FRIDAY, lunch, null,
                        units("Eggs", 4), grams("Oats", 80), millilitres("Milk", 300),
                        grams("Fruit", 150), grams("Yoghurt", 200)),
                mealPlan(person, DayOfWeek.FRIDAY, supper, "Rice or sadza, whichever is available",
                        meat("Lean Beef", 250, null, THAW_HOURS_BEEF), gramsRange("Rice/Sadza", 250, 300),
                        grams("Vegetables", 250), grams("Beans", 100)),

                mealPlan(person, DayOfWeek.SATURDAY, lunch, "Omelette",
                        units("Eggs", 4), grams("Vegetables", 150), grams("Whole-grain Bread", 100),
                        grams("Avocado", 70), grams("Fruit", 150)),
                mealPlan(person, DayOfWeek.SATURDAY, supper, "Chicken or fish; potatoes or rice",
                        meat("Chicken/Fish", 250, 300.0, THAW_HOURS_CHICKEN),
                        gramsRange("Potatoes/Rice", 200, 300), grams("Salad/Vegetables", 300)),

                mealPlan(person, DayOfWeek.SUNDAY, lunch, null,
                        units("Eggs", 4), grams("Oats", 70), millilitres("Milk", 300),
                        grams("Banana", 120), grams("Yoghurt", 200), grams("Nuts", 20)),
                mealPlan(person, DayOfWeek.SUNDAY, supper, "Chicken or beef",
                        meat("Chicken/Beef", 250, 300.0, THAW_HOURS_CHICKEN), grams("Sadza", 250),
                        grams("Covo/Spinach", 250), grams("Beans", 100))));
    }

    private void seedWorkoutPlans(Person person) {
        weeklyWorkoutPlanRepository.saveAll(List.of(

                workoutPlan(person, DayOfWeek.MONDAY, "Push",
                        routine("Bench Press 4x5-8", "Barbell-Bench-Press.gif"),
                        routine("Incline DB Press 3x8-10", "Incline-Dumbbell-Press.gif"),
                        routine("Overhead Press 3x6-8", "Barbell-Standing-Military-Press.gif"),
                        routine("Lateral Raises 3x12-15", "Dumbbell-Lateral-Raise.gif"),
                        routine("Triceps Pushdowns/Dips 3x10-12", "straight-bar-tricep-pushdown.gif")),

                workoutPlan(person, DayOfWeek.TUESDAY, "Pull",
                        routine("Pull-ups/Lat Pulldown 4x6-10", "Lat-Pulldown.gif"),
                        routine("Barbell Row 4x6-10", "Barbell Row-bar-rows.gif"),
                        routine("Seated Cable Row 3x8-12", "Barbell Row-Seated-Cable-Row.gif"),
                        routine("Face Pulls 3x12-15", "Face-Pull.gif"),
                        routine("Biceps Curls 3x8-12", "Barbell-Curl.gif")),

                workoutPlan(person, DayOfWeek.WEDNESDAY, "Legs",
                        routine("Squat 4x5-8", "BARBELL-SQUAT.gif"),
                        routine("Romanian Deadlift 3x8-10", "Barbell-Romanian-Deadlift.gif"),
                        routine("Leg Press 3x8-12", "Leg-Press.gif"),
                        routine("Walking Lunges 3x10/leg", "bodyweight-walking-lunge.gif"),
                        routine("Calf Raises 4x12-15", "Calf-Raises.gif"),
                        routine("Plank 3x45-60 sec", "Plank.gif")),

                workoutPlan(person, DayOfWeek.THURSDAY, "Upper Body",
                        routine("Incline Bench 3x6-10", "Smith-Machine-Incline-Bench-Press.gif"),
                        routine("Lat Pulldown/Pull-ups 3x8-10", "Lat-Pulldown.gif"),
                        routine("DB Shoulder Press 3x8-10", "Standing-Dumbbell-Overhead-Press.gif"),
                        routine("Chest-Supported Row 3x8-12", "Barbell Row-bar-rows.gif"),
                        routine("Lateral Raises 3x12-15", "Dumbbell-Lateral-Raise.gif"),
                        routine("Biceps 2x10-12", "Dumbbell-Curl.gif"),
                        routine("Triceps 2x10-12", "straight-bar-tricep-pushdown.gif")),

                workoutPlan(person, DayOfWeek.FRIDAY, "Lower / Strength",
                        routine("Deadlift 3x4-6", "Barbell-Romanian-Deadlift.gif"),
                        routine("Front Squat/Leg Press 3x6-10", "Leg-Press.gif"),
                        routine("Bulgarian Split Squat 3x8-10/leg", "Barbell-Bulgarian-Split-Squat.gif"),
                        routine("Hip Thrust 3x8-12", "Barbell-Hip-Thrust.gif"),
                        routine("Hamstring Curl 3x10-12", "Seated-Leg-Curl.gif"),
                        routine("Hanging Knee Raises 3x10-15", "Hanging Knee Raises.gif")),

                workoutPlan(person, DayOfWeek.SATURDAY, "Recovery",
                        routine("45-60 min easy walk, light cycling or mobility", null)),

                workoutPlan(person, DayOfWeek.SUNDAY, "Rest",
                        routine("Full rest or easy walking", null))));
    }

    private WeeklyMealPlan mealPlan(Person person, DayOfWeek dayOfWeek, MealSlot mealSlot, String notes,
                                    MealIngredient... ingredients) {
        WeeklyMealPlan plan = WeeklyMealPlan.builder()
                .person(person)
                .dayOfWeek(dayOfWeek)
                .mealSlot(mealSlot)
                .notes(notes)
                .build();
        for (MealIngredient ingredient : ingredients) {
            ingredient.setWeeklyMealPlan(plan);
            plan.getIngredients().add(ingredient);
        }
        return plan;
    }

    private WeeklyWorkoutPlan workoutPlan(Person person, DayOfWeek dayOfWeek, String focus,
                                          WorkoutRoutine... routines) {
        WeeklyWorkoutPlan plan = WeeklyWorkoutPlan.builder()
                .person(person)
                .dayOfWeek(dayOfWeek)
                .focus(focus)
                .build();
        int sortOrder = 0;
        for (WorkoutRoutine routine : routines) {
            routine.setWeeklyWorkoutPlan(plan);
            routine.setSortOrder(sortOrder++);
            plan.getRoutines().add(routine);
        }
        return plan;
    }

    private static WorkoutRoutine routine(String name, String example) {
        return WorkoutRoutine.builder()
                .name(name)
                .example(example)
                .build();
    }

    private static MealIngredient grams(String foodName, double qty) {
        return ingredient(foodName, qty, null, "g", false, null);
    }

    private static MealIngredient gramsRange(String foodName, double qty, double qtyMax) {
        return ingredient(foodName, qty, qtyMax, "g", false, null);
    }

    private static MealIngredient millilitres(String foodName, double qty) {
        return ingredient(foodName, qty, null, "ml", false, null);
    }

    private static MealIngredient units(String foodName, double qty) {
        return ingredient(foodName, qty, null, "unit", false, null);
    }

    private static MealIngredient meat(String foodName, double qty, Double qtyMax, int thawHours) {
        return ingredient(foodName, qty, qtyMax, "g", true, thawHours);
    }

    private static MealIngredient ingredient(String foodName, double qty, Double qtyMax, String unit,
                                             boolean meat, Integer thawHours) {
        return MealIngredient.builder()
                .foodName(foodName)
                .qty(qty)
                .qtyMax(qtyMax)
                .unit(unit)
                .meat(meat)
                .thawHoursNeeded(thawHours)
                .build();
    }
}
