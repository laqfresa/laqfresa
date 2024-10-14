package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.detailProduct.DetailProduct;

@ApplicationScoped
public class DetailProductRepository implements PanacheRepository<DetailProduct> {

}
