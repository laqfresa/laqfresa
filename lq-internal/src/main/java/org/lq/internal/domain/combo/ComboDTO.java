package org.lq.internal.domain.combo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComboDTO {

    @NotNull(message = "El campo name (nombre) no puede ser nulo o estar vacío.")
    private String name;

    @NotNull(message = "El campo nameDiscount (nombre descuento) no puede ser nulo o estar vacío.")
    private String nameDiscount;

    @NotNull(message = "El campo description (descripción) no puede ser nulo o estar vacío.")
    private String description;

    @NotNull(message = "El campo value (precio) no puede ser nulo o estar vacío.")
    @Positive(message = "El campo value (precio) no puede ser igual o menor a cero.")
    private Long value;

    @NotEmpty(message = "El detailOrders (detalles del pedido) no puede ser nulo o estar vacío")
    private List<DetailCombo> detailCombos;
}
