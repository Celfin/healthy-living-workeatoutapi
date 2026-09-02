package co.zw.celfin.healthyliving.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class GroceryItemUpdateRequest {

    // partial update: only ticking off purchase / recording price, so all fields are optional
    private Double boughtQty;
    private BigDecimal unitPrice;
    private BigDecimal totalSpent;
    private Boolean bought;
}
