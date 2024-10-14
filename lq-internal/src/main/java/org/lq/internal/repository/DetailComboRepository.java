package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.combo.DetailCombo;
import org.lq.internal.domain.product.Product;

@ApplicationScoped
public class DetailComboRepository implements PanacheRepository<DetailCombo> {

}
