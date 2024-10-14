package org.lq.internal.domain.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lq.internal.domain.detailOrder.DetailOrder;
import org.lq.internal.domain.ingredient.DetailAdditional;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotNull(message = "El idUser (id de usuario) no puede ser nulo o estar vacío")
    private Long idUser;

    @NotNull(message = "El total (total del pedido) no puede ser nulo o estar vacío")
    private Long total;

    @NotNull(message = "El campo de fecha de creacion (creationDate) no puede ser nulo o estar vacío")
    private String creationDate;

    private Long discount;

    @NotEmpty(message = "El detailOrders (detalles del pedido) no puede ser nulo o estar vacío")
    private List<DetailOrder> detailOrders;
}
