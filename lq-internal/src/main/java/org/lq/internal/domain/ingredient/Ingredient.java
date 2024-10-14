package org.lq.internal.domain.ingredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INGREDIENTE")
public class Ingredient {

    @Id
    @Column(name = "id_ingrediente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientId;

    @Column(name = "id_tipo_ingrediente")
    private int ingredientType;

    @Column(name = "nombre")
    private String name;

    @Column(name = "activo")
    private boolean active;

}
