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
@Table(name = "TIPO_INGREDIENTE")
public class TypeIngredient {

    @Id
    @Column(name = "ID_TIPO_INGREDIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientTypeId;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "ACTIVO")
    private boolean active;

    @Column(name = "VALOR")
    private long value;
}