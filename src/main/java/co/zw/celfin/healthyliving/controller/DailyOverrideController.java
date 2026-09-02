package co.zw.celfin.healthyliving.controller;

import co.zw.celfin.healthyliving.dto.ApiResponse;
import co.zw.celfin.healthyliving.dto.DailyOverrideDto;
import co.zw.celfin.healthyliving.dto.DailyOverrideRequest;
import co.zw.celfin.healthyliving.service.DailyOverrideService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-overrides")
@RequiredArgsConstructor
@Tag(name = "Daily Overrides", description = "One-off deviations from the weekly template for a specific date, e.g. eating out or a meal swap")
public class DailyOverrideController {

    private final DailyOverrideService dailyOverrideService;

    @GetMapping
    public ApiResponse<List<DailyOverrideDto>> findByPersonAndDate(
            @RequestParam Long personId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ApiResponse.ok(dailyOverrideService.findByPersonAndDate(personId, date));
    }

    @PostMapping
    public ApiResponse<DailyOverrideDto> create(@Valid @RequestBody DailyOverrideRequest request) {
        return ApiResponse.created(dailyOverrideService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<DailyOverrideDto> update(@PathVariable Long id, @Valid @RequestBody DailyOverrideRequest request) {
        return ApiResponse.ok(dailyOverrideService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        dailyOverrideService.delete(id);
        return ApiResponse.ok(null);
    }
}
