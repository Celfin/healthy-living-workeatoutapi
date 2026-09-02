package co.zw.celfin.healthyliving.controller;

import co.zw.celfin.healthyliving.dto.ApiResponse;
import co.zw.celfin.healthyliving.dto.GroceryItemDto;
import co.zw.celfin.healthyliving.dto.GroceryItemUpdateRequest;
import co.zw.celfin.healthyliving.service.GroceryListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grocery-list")
@RequiredArgsConstructor
@Tag(name = "Grocery List", description = "Monthly grocery list derived from the weekly meal plans, with bought/price tracking")
public class GroceryListController {

    private final GroceryListService groceryListService;

    @GetMapping
    public ApiResponse<List<GroceryItemDto>> findByMonth(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return ApiResponse.ok(groceryListService.findByMonth(month));
    }

    @PostMapping("/generate")
    public ApiResponse<List<GroceryItemDto>> generate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return ApiResponse.ok(groceryListService.generate(month));
    }

    @PatchMapping("/{id}")
    public ApiResponse<GroceryItemDto> update(@PathVariable Long id, @Valid @RequestBody GroceryItemUpdateRequest request) {
        return ApiResponse.ok(groceryListService.update(id, request));
    }
}
