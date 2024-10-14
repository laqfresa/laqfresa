package org.lq.internal.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USUARIO")
public class User {

    @Id
    @Column(name = "numero_documento")
    private Long documentNumber;

    @Column(name = "id_tipo_usuario")
    private int userTypeId;

    @Column(name = "id_genero")
    private int genderId;

    @Column(name = "id_tipo_documento")
    private int documentTypeId;

    @Column(name = "id_estado_usuario")
    private int userStatusId;

    @Column(name = "nombre_uno")
    private String firstName;

    @Column(name = "nombre_dos")
    private String secondName;

    @Column(name = "apellido_uno")
    private String firstLastName;

    @Column(name = "apellido_dos")
    private String secondLastName;

    @Column(name = "telefono")
    private String phone;

    @Column(name = "direccion")
    private String address;

    @Column(name = "correo")
    private String email;

    @Column(name = "contraseña")
    private String password;
}
