package org.lq.internal.domain.size;

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
@Table(name = "TAMANIO")
public class Size implements Serializable {

    @Id
    @Column(name = "id_tamanio", length = 36)
    private int idTamanio;

    @Column(name = "tamanio", length = 36)
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_tamanio", referencedColumnName = "id_tipo_tamanio")
    private TypeSize typeSize;
}
