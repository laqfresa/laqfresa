package org.lq.internal.domain.user;

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
@Table(name = "USUARIO")
public class UserData {
    @Id
    @Column(name = "numero_documento")
    private Long documentNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_tipo_usuario")
    private TypeUser userType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_genero")
    private GenderUser gender;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_tipo_documento")
    private TypeDocument documentTypeId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_estado_usuario")
    private StatusUser userStatus;

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

