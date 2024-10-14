package org.lq.internal.domain.ingredient;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "INGREDIENTE")
public class IngredientData {

    @Id
    @Column(name = "ID_INGREDIENTE")
    private int ingredientId;

    @JoinColumn(name = "ID_TIPO_INGREDIENTE")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private TypeIngredient ingredientType;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "ACTIVO")
    private boolean active;
}
