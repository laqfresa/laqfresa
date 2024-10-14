package org.lq.internal.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TIPO_DOCUMENTO")
public class TypeDocument {

    @Id
    @Column(name = "id_tipo_documento")
    private int idTypeDocument;

    @Column(name = "nombre")
    private String name;
}
