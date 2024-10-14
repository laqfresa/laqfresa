package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.detailOrder.DetailOrder;

import java.util.List;

@ApplicationScoped
public class DetailOrderRepository implements PanacheRepository<DetailOrder> {

    public List<DetailOrder> findByIdOrderWithProduct(int idOrder) {
        return getEntityManager().createQuery(
                        "SELECT do FROM DetailOrder do JOIN FETCH do.product WHERE do.idOrder = :idOrder", DetailOrder.class)
                .setParameter("idOrder", idOrder)
                .getResultList();
    }
}
