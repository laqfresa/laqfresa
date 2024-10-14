package org.lq.internal.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "GENERO")
public class GenderUser {

    @Id
    @Column(name = "id_genero")
    private int idGender;

    @Column(name = "nombre")
    private String name;
}
