package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.user.StatusUser;

@ApplicationScoped
public class StatusUserRepository implements PanacheRepository<StatusUser> {
}
