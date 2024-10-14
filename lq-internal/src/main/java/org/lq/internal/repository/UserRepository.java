package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.user.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByDocumentNumber(long documentNumber) {
        return find("documentNumber", documentNumber).firstResult();
    }
}
