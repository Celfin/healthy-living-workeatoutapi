package co.zw.celfin.healthyliving.service;

import co.zw.celfin.healthyliving.dto.GroceryItemDto;
import co.zw.celfin.healthyliving.dto.GroceryItemUpdateRequest;
import java.time.YearMonth;
import java.util.List;

public interface GroceryListService {

    List<GroceryItemDto> findByMonth(YearMonth month);

    /**
     * (Re)computes plannedQty for every ingredient across all people's weekly meal plans,
     * multiplied by how many times each day-of-week occurs in the given month.
     * Existing bought/price data for items that still appear is preserved.
     */
    List<GroceryItemDto> generate(YearMonth month);

    GroceryItemDto update(Long id, GroceryItemUpdateRequest request);
}
