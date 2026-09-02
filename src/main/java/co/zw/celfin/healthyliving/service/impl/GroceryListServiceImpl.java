package co.zw.celfin.healthyliving.service.impl;

import co.zw.celfin.healthyliving.dto.GroceryItemDto;
import co.zw.celfin.healthyliving.dto.GroceryItemUpdateRequest;
import co.zw.celfin.healthyliving.entity.GroceryItem;
import co.zw.celfin.healthyliving.entity.WeeklyMealPlan;
import co.zw.celfin.healthyliving.exception.ResourceNotFoundException;
import co.zw.celfin.healthyliving.mapper.GroceryItemMapper;
import co.zw.celfin.healthyliving.repository.GroceryItemRepository;
import co.zw.celfin.healthyliving.repository.WeeklyMealPlanRepository;
import co.zw.celfin.healthyliving.service.GroceryListService;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryItemRepository groceryItemRepository;
    private final WeeklyMealPlanRepository weeklyMealPlanRepository;
    private final GroceryItemMapper groceryItemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GroceryItemDto> findByMonth(YearMonth month) {
        return groceryItemRepository.findByMonthKeyOrderByFoodNameAsc(month.toString()).stream()
                .map(groceryItemMapper::toDto)
                .toList();
    }

    @Override
    public List<GroceryItemDto> generate(YearMonth month) {
        String monthKey = month.toString();
        Map<DayOfWeek, Long> occurrencesByDay = countDayOfWeekOccurrences(month);

        // key = "foodName|unit"
        Map<String, Double> plannedQtyByFood = new HashMap<>();

        for (WeeklyMealPlan plan : weeklyMealPlanRepository.findAll()) {
            long occurrences = occurrencesByDay.getOrDefault(plan.getDayOfWeek(), 0L);
            if (occurrences == 0) {
                continue;
            }
            plan.getIngredients().forEach(ingredient -> {
                double perOccurrence = ingredient.getQtyMax() != null ? ingredient.getQtyMax() : ingredient.getQty();
                String key = ingredient.getFoodName().trim().toLowerCase() + "|" + ingredient.getUnit();
                plannedQtyByFood.merge(key, perOccurrence * occurrences, Double::sum);
            });
        }

        plannedQtyByFood.forEach((key, plannedQty) -> {
            String[] parts = key.split("\\|", 2);
            String foodName = parts[0];
            String unit = parts[1];

            GroceryItem item = groceryItemRepository.findByMonthKeyAndFoodNameAndUnit(monthKey, foodName, unit)
                    .orElseGet(() -> GroceryItem.builder()
                            .monthKey(monthKey)
                            .foodName(foodName)
                            .unit(unit)
                            .bought(false)
                            .build());
            item.setPlannedQty(plannedQty);
            groceryItemRepository.save(item);
        });

        return findByMonth(month);
    }

    @Override
    public GroceryItemDto update(Long id, GroceryItemUpdateRequest request) {
        GroceryItem item = groceryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grocery item not found: " + id));

        if (request.getBoughtQty() != null) {
            item.setBoughtQty(request.getBoughtQty());
        }
        if (request.getUnitPrice() != null) {
            item.setUnitPrice(request.getUnitPrice());
        }
        if (request.getTotalSpent() != null) {
            item.setTotalSpent(request.getTotalSpent());
        }
        if (request.getBought() != null) {
            item.setBought(request.getBought());
        }
        return groceryItemMapper.toDto(groceryItemRepository.save(item));
    }

    private Map<DayOfWeek, Long> countDayOfWeekOccurrences(YearMonth month) {
        Map<DayOfWeek, Long> counts = new HashMap<>();
        int daysInMonth = month.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            DayOfWeek dow = month.atDay(day).getDayOfWeek();
            counts.merge(dow, 1L, Long::sum);
        }
        return counts;
    }
}
