package org.lq.internal.domain.combo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lq.internal.domain.detailProduct.DetailProduct;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMBO")
public class Combo implements Serializable {

    @Id
    @Column(name = "ID_COMBO", length = 36)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCombo;

    @Column(name = "ID_TIPO_DESCUENTO", length = 36)
    private int idTypeDiscount;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "DESCRIPCION")
    private String description;

    @Column(name = "VALOR")
    private Long value;

    @Column(name = "ESTADO")
    private String status;

    @Transient
    private List<DetailCombo> detailCombos;
}
