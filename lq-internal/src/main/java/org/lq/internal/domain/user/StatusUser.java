package org.lq.internal.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ESTADO_USUARIO")
public class StatusUser {

    @Id
    @Column(name = "id_estado_usuario")
    private int idStatusUser;

    @Column(name = "nombre")
    private String name;
}

