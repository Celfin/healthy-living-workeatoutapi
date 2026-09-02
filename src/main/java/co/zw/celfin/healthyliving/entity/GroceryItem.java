package co.zw.celfin.healthyliving.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grocery_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_key", nullable = false)
    private String monthKey; // "2026-08"

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String unit;

    @Column(name = "planned_qty", nullable = false)
    private Double plannedQty;

    @Column(name = "bought_qty")
    private Double boughtQty;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_spent", precision = 10, scale = 2)
    private BigDecimal totalSpent;

    @Column(nullable = false)
    private boolean bought;
}
