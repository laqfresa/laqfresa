package org.lq.internal.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sales {

    private BigDecimal totalDailySales;
    private BigDecimal totalWeeklySales;
    private BigDecimal totalMonthlySales;
    private BigDecimal totalSales;
}
