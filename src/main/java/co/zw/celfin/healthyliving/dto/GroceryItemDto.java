package co.zw.celfin.healthyliving.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroceryItemDto {
    private Long id;
    private String monthKey;
    private String foodName;
    private String unit;
    private Double plannedQty;
    private Double boughtQty;
    private BigDecimal unitPrice;
    private BigDecimal totalSpent;
    private boolean bought;
}
