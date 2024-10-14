package org.lq.internal.domain.detailProduct;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lq.internal.domain.ingredient.DetailAdditional;
import org.lq.internal.domain.ingredient.Ingredient;
import org.lq.internal.domain.ingredient.IngredientData;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DETALLE_PRODUCTO")
public class DetailProduct implements Serializable {

    @Id
    @Column(name = "ID_DETALLE_PRODUCTO", length = 36)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetailProduct;

    @Column(name = "ID_INGREDIENTE", length = 36)
    private int idIngredient;

    @Column(name = "ID_PRODUCTO", length = 36)
    private int idProduct;

    @Column(name = "CANTIDAD")
    private Long quantity;

    @Transient
    private IngredientData ingredient;
}
