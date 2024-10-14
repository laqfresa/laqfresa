package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.size.Size;

@ApplicationScoped
public class SizeRepository implements PanacheRepository<Size> {

}
