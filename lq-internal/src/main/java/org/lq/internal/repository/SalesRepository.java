package org.lq.internal.repository;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.lq.internal.domain.sales.SalesDaily;
import org.lq.internal.domain.sales.SalesMonth;
import org.lq.internal.domain.sales.SalesWeek;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class SalesRepository {

    private static final Logger LOG = Logger.getLogger(SalesRepository.class);

    private static final String QUERY_GET_SALES_DAILY = """
                                                            SELECT
                                                                DATE(fecha_hora) AS fecha,
                                                                SUM(total) AS total_diario
                                                            FROM PEDIDO
                                                            WHERE DATE(fecha_hora) = ? AND estado = 'COMPLETADO'
                                                            GROUP BY DATE(fecha_hora)
                                                            ORDER BY fecha
                                                        """;

    private static final String QUERY_GET_SALES_WEEKLY = """
                                                            SELECT
                                                                ? AS inicio_semana,
                                                                DATE_ADD(?, INTERVAL 7 DAY) AS fin_semana,
                                                                SUM(total) AS total_semanal
                                                            FROM PEDIDO
                                                            WHERE fecha_hora BETWEEN ? AND DATE_ADD(?, INTERVAL 7 DAY)
                                                            AND estado = 'COMPLETADO'
                                                        """;

    private static final String QUERY_GET_SALES_MONTHLY = """
                                                            SELECT
                                                                DATE_FORMAT(fecha_hora, '%Y-%m') AS mes,
                                                                SUM(total) AS total_mensual
                                                            FROM PEDIDO
                                                            WHERE DATE_FORMAT(fecha_hora, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') AND estado = 'COMPLETADO'
                                                            GROUP BY DATE_FORMAT(fecha_hora, '%Y-%m')
                                                            ORDER BY mes
                                                        """;

    private static final String QUERY_GET_SALES_BY_DATE_RANGE = """
                                                            SELECT
                                                                SUM(total) AS total_ventas
                                                            FROM PEDIDO
                                                            WHERE fecha_hora BETWEEN ? AND ? AND estado = 'COMPLETADO'
                                                        """;

    private static final String QUERY_GET_TOTAL_SALES = """
                                                            SELECT
                                                                SUM(total) AS total_ventas
                                                            FROM PEDIDO
                                                            WHERE estado = 'COMPLETADO'
                                                        """;

    @Inject
    AgroalDataSource hacvDataSource;

    public SalesDaily getListSalesDaily(String date) throws SQLException {
        LOG.infof("@getListSalesDaily REPO > Start query to obtain daily sales for date %s", date);

        LOG.info("@getListSalesDaily REPO > Start query execution");

        SalesDaily sales = null;

        try (Connection connection = hacvDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_GET_SALES_DAILY)) {

            ps.setString(1, date);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                sales = SalesDaily.builder()
                        .date(String.valueOf(resultSet.getDate("fecha")))
                        .totalDailySales(resultSet.getBigDecimal("total_diario"))
                        .build();
            }
            LOG.info("@getListSalesDaily REPO > Finish building the list with the data obtained");
        } catch (SQLException e) {
            LOG.error("@getListSalesDaily REPO > Error executing query", e);
            throw e;
        }

        LOG.infof("@getListSalesDaily REPO > Ends query to obtain daily sales for date %s", date);
        return sales;
    }

    public SalesWeek getListSalesWeekly(String startDate) throws SQLException {
        LOG.infof("@getListSalesWeekly REPO > Inicia consulta para obtener ventas semanales desde la fecha %s", startDate);

        SalesWeek sales = null;

        try (Connection connection = hacvDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_GET_SALES_WEEKLY)) {

            ps.setString(1, startDate);
            ps.setString(2, startDate);
            ps.setString(3, startDate);
            ps.setString(4, startDate);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                sales = SalesWeek.builder()
                        .weekInitial(resultSet.getString("inicio_semana"))
                        .weekFinish(resultSet.getString("fin_semana"))
                        .totalDailyWeek(resultSet.getBigDecimal("total_semanal"))
                        .build();
            }
            LOG.info("@getListSalesWeekly REPO > Finaliza construcción de la lista con los datos obtenidos");
        } catch (SQLException e) {
            LOG.error("@getListSalesWeekly REPO > Error ejecutando la consulta", e);
            throw e;
        }

        LOG.infof("@getListSalesWeekly REPO > Termina consulta para obtener ventas semanales desde la fecha %s", startDate);
        return sales;
    }

    public SalesMonth getListSalesMonthly(String date) throws SQLException {
        LOG.infof("@getListSalesMonthly REPO > Start query to obtain monthly sales for date %s", date);
        SalesMonth sales = null;

        LOG.info("@getListSalesMonthly REPO > Start query execution");

        try (Connection connection = hacvDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_GET_SALES_MONTHLY)) {

            ps.setString(1, date);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                sales = SalesMonth.builder()
                        .month(resultSet.getString("mes"))
                        .totalDailyMonth(resultSet.getBigDecimal("total_mensual"))
                        .build();
            }
            LOG.info("@getListSalesMonthly REPO > Finish building the list with the data obtained");
        } catch (SQLException e) {
            LOG.error("@getListSalesMonthly REPO > Error executing query", e);
            throw e;
        }

        LOG.infof("@getListSalesMonthly REPO > Ends query to obtain monthly sales for date %s", date);
        return sales;
    }

    public BigDecimal getSalesByDateRange(String startDate, String endDate) throws SQLException {
        String startDateTime = startDate + " 00:00:00";
        String endDateTime = endDate + " 23:59:59";

        try (Connection connection = hacvDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_GET_SALES_BY_DATE_RANGE)) {

            ps.setString(1, startDateTime);
            ps.setString(2, endDateTime);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("total_ventas");
            } else {
                return BigDecimal.ZERO;
            }
        }
    }

    public BigDecimal getTotalSales() throws SQLException {
        try (Connection connection = hacvDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_GET_TOTAL_SALES)) {

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("total_ventas");
            } else {
                return BigDecimal.ZERO;
            }
        }
    }
}
