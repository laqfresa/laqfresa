package org.lq.internal.domain.combo;

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
@Table(name = "TIPO_DESCUENTO")
public class TypeDiscount {

    @Id
    @Column(name = "ID_TIPO_DESCUENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypeDiscount;

    @Column(name = "NOMBRE")
    private String name;
}