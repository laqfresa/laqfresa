package org.lq.internal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lq.internal.domain.ingredient.IngredientData;

import java.util.List;

@ApplicationScoped
public class IngredientDataRepository implements PanacheRepository<IngredientData> {

    public List<IngredientData> findActiveIngredients() {
        return list("active = true");
    }
}
