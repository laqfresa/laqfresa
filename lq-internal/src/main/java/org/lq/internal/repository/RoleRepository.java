package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.user.TypeUser;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<TypeUser> {
}
