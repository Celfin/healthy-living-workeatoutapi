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
 * Loads the 5-day gym and meal plan on first startup, transcribed from
 * 5_day_gym_and_meal_plan.xlsx (the "Weekly Plan" and "Workout Summary" sheets).
 *
 * Seeding is skipped once any person exists, so this is a no-op on every restart
 * after the first and never overwrites edits made through the API.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    /** Hours of fridge thawing assumed for each kind of meat. */
    private static final int THAW_HOURS_RED_MEAT = 12;
    private static final int THAW_HOURS_FISH = 8;

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

        Person adult = personRepository.save(Person.builder()
                .name("Adult")
                .type(PersonType.ADULT)
                .build());

        MealSlot meal1 = mealSlotRepository.save(MealSlot.builder()
                .person(adult)
                .name("Meal 1")
                .sortOrder(1)
                .defaultTime(LocalTime.of(8, 0))
                .build());
        MealSlot meal2 = mealSlotRepository.save(MealSlot.builder()
                .person(adult)
                .name("Meal 2")
                .sortOrder(2)
                .defaultTime(LocalTime.of(13, 0))
                .build());

        seedMealPlans(adult, meal1, meal2);
        seedWorkoutPlans(adult);

        log.info("Seeded {} meal plan(s) and {} workout plan(s) for {}",
                weeklyMealPlanRepository.count(), weeklyWorkoutPlanRepository.count(), adult.getName());
    }

    private void seedMealPlans(Person person, MealSlot meal1, MealSlot meal2) {
        weeklyMealPlanRepository.saveAll(List.of(

                mealPlan(person, DayOfWeek.MONDAY, meal1, null,
                        units("Eggs", 4), grams("Oats", 80), millilitres("Milk", 300),
                        grams("Banana", 120), grams("Avocado", 70)),
                mealPlan(person, DayOfWeek.MONDAY, meal2, null,
                        meat("Chicken", 250, 300.0, THAW_HOURS_RED_MEAT), grams("Cooked rice", 250),
                        grams("Covo/spinach", 200), grams("Salad", 150), millilitres("Olive oil", 10)),

                mealPlan(person, DayOfWeek.TUESDAY, meal1, null,
                        units("Eggs", 3), grams("Plain/Greek yoghurt", 300), grams("Oats", 70),
                        grams("Apple/orange", 150), grams("Nuts", 25)),
                mealPlan(person, DayOfWeek.TUESDAY, meal2, null,
                        meat("Lean beef", 250, null, THAW_HOURS_RED_MEAT), grams("Potatoes", 300),
                        grams("Mixed vegetables", 250), grams("Avocado", 70)),

                mealPlan(person, DayOfWeek.WEDNESDAY, meal1, null,
                        units("Eggs", 4), grams("Whole-grain bread", 100), grams("Avocado", 70),
                        grams("Banana", 120), grams("Yoghurt", 200)),
                mealPlan(person, DayOfWeek.WEDNESDAY, meal2, null,
                        meat("Chicken", 250, 300.0, THAW_HOURS_RED_MEAT), grams("Rice", 250),
                        grams("Beans", 120), grams("Covo/spinach", 200)),

                mealPlan(person, DayOfWeek.THURSDAY, meal1, null,
                        grams("Oats", 80), millilitres("Milk", 300), units("Eggs", 3),
                        grams("Peanut butter", 20), grams("Banana", 120)),
                mealPlan(person, DayOfWeek.THURSDAY, meal2, null,
                        meat("Fish", 250, 300.0, THAW_HOURS_FISH), grams("Sweet potato", 300),
                        grams("Vegetables", 250), grams("Avocado", 70)),

                mealPlan(person, DayOfWeek.FRIDAY, meal1, null,
                        units("Eggs", 4), grams("Oats", 80), millilitres("Milk", 300),
                        grams("Fruit", 150), grams("Yoghurt", 200)),
                mealPlan(person, DayOfWeek.FRIDAY, meal2, "Rice 250 g may be swapped for sadza 250-300 g",
                        meat("Lean beef", 250, null, THAW_HOURS_RED_MEAT), grams("Rice", 250),
                        grams("Vegetables", 250), grams("Beans", 100)),

                mealPlan(person, DayOfWeek.SATURDAY, meal1, "Served as an omelette",
                        units("Eggs", 4), grams("Vegetables", 150), grams("Whole-grain bread", 100),
                        grams("Avocado", 70), grams("Fruit", 150)),
                mealPlan(person, DayOfWeek.SATURDAY, meal2, "Potatoes 300 g may be swapped for rice 200 g",
                        meat("Chicken or fish", 250, 300.0, THAW_HOURS_RED_MEAT), grams("Potatoes", 300),
                        grams("Salad/vegetables", 300)),

                mealPlan(person, DayOfWeek.SUNDAY, meal1, null,
                        units("Eggs", 4), grams("Oats", 70), millilitres("Milk", 300),
                        grams("Banana", 120), grams("Yoghurt", 200), grams("Nuts", 20)),
                mealPlan(person, DayOfWeek.SUNDAY, meal2, null,
                        meat("Chicken/beef", 250, 300.0, THAW_HOURS_RED_MEAT), grams("Sadza", 250),
                        grams("Covo/spinach", 250), grams("Beans", 100))));
    }

    private void seedWorkoutPlans(Person person) {
        weeklyWorkoutPlanRepository.saveAll(List.of(

                workoutPlan(person, DayOfWeek.MONDAY, "Push",
                        "Bench Press 4x5-8",
                        "Incline DB Press 3x8-10",
                        "Overhead Press 3x6-8",
                        "Lateral Raises 3x12-15",
                        "Triceps Pushdowns/Dips 3x10-12"),

                workoutPlan(person, DayOfWeek.TUESDAY, "Pull",
                        "Pull-ups/Lat Pulldown 4x6-10",
                        "Barbell Row 4x6-10",
                        "Seated Cable Row 3x8-12",
                        "Face Pulls 3x12-15",
                        "Biceps Curls 3x8-12"),

                workoutPlan(person, DayOfWeek.WEDNESDAY, "Legs",
                        "Squat 4x5-8",
                        "Romanian Deadlift 3x8-10",
                        "Leg Press 3x8-12",
                        "Walking Lunges 3x10/leg",
                        "Calf Raises 4x12-15",
                        "Plank 3x45-60 sec"),

                workoutPlan(person, DayOfWeek.THURSDAY, "Upper Body",
                        "Incline Bench 3x6-10",
                        "Lat Pulldown/Pull-ups 3x8-10",
                        "DB Shoulder Press 3x8-10",
                        "Chest-Supported Row 3x8-12",
                        "Lateral Raises 3x12-15",
                        "Biceps 2x10-12",
                        "Triceps 2x10-12"),

                workoutPlan(person, DayOfWeek.FRIDAY, "Lower / Strength",
                        "Deadlift 3x4-6",
                        "Front Squat/Leg Press 3x6-10",
                        "Bulgarian Split Squat 3x8-10/leg",
                        "Hip Thrust 3x8-12",
                        "Hamstring Curl 3x10-12",
                        "Hanging Knee Raises 3x10-15"),

                workoutPlan(person, DayOfWeek.SATURDAY, "Recovery",
                        "45-60 min easy walk, light cycling, or mobility"),

                workoutPlan(person, DayOfWeek.SUNDAY, "Rest",
                        "Full rest or easy walking")));
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

    private WeeklyWorkoutPlan workoutPlan(Person person, DayOfWeek dayOfWeek, String focus, String... routines) {
        WeeklyWorkoutPlan plan = WeeklyWorkoutPlan.builder()
                .person(person)
                .dayOfWeek(dayOfWeek)
                .focus(focus)
                .build();
        int sortOrder = 1;
        for (String routine : routines) {
            plan.getRoutines().add(WorkoutRoutine.builder()
                    .weeklyWorkoutPlan(plan)
                    .sortOrder(sortOrder++)
                    .name(routine)
                    .build());
        }
        return plan;
    }

    private static MealIngredient grams(String foodName, double qty) {
        return ingredient(foodName, qty, null, "g", false, null);
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
