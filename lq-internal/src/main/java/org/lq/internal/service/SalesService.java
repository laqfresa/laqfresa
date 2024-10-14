package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lq.internal.domain.sales.SalesDaily;
import org.lq.internal.domain.sales.SalesMonth;
import org.lq.internal.domain.sales.SalesWeek;
import org.lq.internal.repository.SalesRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

@ApplicationScoped
public class SalesService {

    @Inject
    SalesRepository salesRepository;

    public BigDecimal getTotalOrders() throws SQLException {
        return salesRepository.getTotalSales();
    }

    public SalesDaily getTotalOrdersDaily(String date) throws SQLException {
        return salesRepository.getListSalesDaily(date);
    }

    public SalesWeek getTotalOrdersWeek(String date) throws SQLException {
        return salesRepository.getListSalesWeekly(date);
    }

    public SalesMonth getTotalOrdersMonth(String date) throws SQLException {
        return salesRepository.getListSalesMonthly(date);
    }

    public BigDecimal getTotalOrdersByRange(String dateInitial, String dateFinish) throws SQLException {
        return salesRepository.getSalesByDateRange(dateInitial, dateFinish);
    }
}
