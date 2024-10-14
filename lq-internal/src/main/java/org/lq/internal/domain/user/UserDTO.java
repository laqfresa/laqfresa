package org.lq.internal.domain.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "El campo documentNumber (número de documento) no puede ser nulo.")
    @Positive(message = "El campo documentNumber (número de documento) debe ser un número positivo.")
    private long documentNumber;

    @NotNull(message = "El campo userTypeId (ID de tipo de usuario) no puede ser nulo.")
    @Positive(message = "El campo userTypeId (ID de tipo de usuario) debe ser un número positivo.")
    private int userTypeId;

    @NotNull(message = "El campo genderId (ID de género) no puede ser nulo.")
    @Positive(message = "El campo genderId (ID de género) debe ser un número positivo.")
    private int genderId;

    @NotNull(message = "El campo documentTypeId (ID de tipo de documento) no puede ser nulo.")
    @Positive(message = "El campo documentTypeId (ID de tipo de documento) debe ser un número positivo.")
    private int documentTypeId;

    @NotNull(message = "El campo userStatusId (ID de estado de usuario) no puede ser nulo.")
    @Positive(message = "El campo userStatusId (ID de estado de usuario) debe ser un número positivo.")
    private int userStatusId;

    @NotEmpty(message = "El campo firstName (primer nombre) no puede ser nulo ni estar vacío.")
    @Size(max = 50, message = "El campo firstName (primer nombre) no puede tener más de 50 caracteres.")
    private String firstName;

    @Size(max = 50, message = "El campo secondName (segundo nombre) no puede tener más de 50 caracteres.")
    private String secondName;

    @NotEmpty(message = "El campo firstLastName (primer apellido) no puede ser nulo ni estar vacío.")
    @Size(max = 50, message = "El campo firstLastName (primer apellido) no puede tener más de 50 caracteres.")
    private String firstLastName;

    @Size(max = 50, message = "El campo secondLastName (segundo apellido) no puede tener más de 50 caracteres.")
    private String secondLastName;

    @NotEmpty(message = "El campo phone (teléfono) no puede ser nulo ni estar vacío.")
    @Size(max = 20, message = "El campo phone (teléfono) no puede tener más de 20 caracteres.")
    private String phone;

    @NotEmpty(message = "El campo address (dirección) no puede ser nulo ni estar vacío.")
    @Size(max = 100, message = "El campo address (dirección) no puede tener más de 100 caracteres.")
    private String address;

    @NotEmpty(message = "El campo email (correo) no puede ser nulo ni estar vacío.")
    @Email(message = "El campo email (correo) debe ser una dirección de correo válida.")
    @Size(max = 100, message = "El campo email (correo) no puede tener más de 100 caracteres.")
    private String email;

    @NotEmpty(message = "El campo password (contraseña) no puede ser nulo ni estar vacío.")
    @Size(min = 8, message = "El campo password (contraseña) debe tener al menos 8 caracteres.")
    private String password;

}
