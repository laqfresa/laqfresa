package org.lq.internal.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "TIPO_USUARIO")
public class TypeUser {

    @Id
    @Column(name = "id_tipo_usuario")
    private int idTypeUser;

    @Column(name = "nombre")
    private String name;
}
