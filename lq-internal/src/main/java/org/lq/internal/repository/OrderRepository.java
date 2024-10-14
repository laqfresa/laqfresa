package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.order.Order;
import org.lq.internal.domain.order.OrderStatus;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findOrdersPending() {
        return list("status", OrderStatus.PENDIENTE);
    }

    public List<Order> findOrdersCompleted() {
        return list("status", OrderStatus.COMPLETADO);
    }

    public Optional<Order> findOrdersPendingNumber(Long orderId) {
        return list("idOrder = ?1 and status = ?2", orderId, OrderStatus.PENDIENTE).stream().findFirst();
    }

    public Optional<Order> findOrdersProcesoNumber(Long orderId) {
        return list("idOrder = ?1 and status = ?2", orderId, OrderStatus.PROCESO).stream().findFirst();
    }
}
