package org.lq.internal.domain.ingredient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DETALLE_ADICIONAL")
public class DetailAdditional implements Serializable {

    @Id
    @Column(name = "ID_DETALLE_ADICIONAL", length = 36)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetailAdditional;

    @Column(name = "ID_DETALLE_PEDIDO", length = 36)
    private int idDetailOrder;

    @Column(name = "ID_INGREDIENTE", length = 36)
    private int ingredientId;

    @Transient
    private IngredientData ingredient;
}
