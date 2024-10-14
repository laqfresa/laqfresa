package org.lq.internal.domain.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.lq.internal.domain.detailProduct.DetailProduct;

import java.util.List;

@Data
public class ProductDTO {

    @NotNull(message = "El campo prdLvlNumber (codigo de producto) no puede ser nulo o estar vacío.")
    private int idProduct;

    @NotNull(message = "El campo size (codigo de tamaño) no puede ser nulo o estar vacío.")
    private int size;

    @NotNull(message = "El campo name (nombre) no puede ser nulo o estar vacío.")
    private String name;

    @NotNull(message = "El campo description (descripción) no puede ser nulo o estar vacío.")
    private String description;

    @NotNull(message = "El campo value (precio) no puede ser nulo o estar vacío.")
    @Positive(message = "El campo value (precio) no puede ser igual o menor a cero.")
    private Long value;

    private int quantityClasic;

    private int quantityPremium;

    private int quantitySalsa;

    private String status;

    @NotEmpty(message = "Los campos del detalle del producto no pueden estar vacios.")
    private List<DetailProduct> detailProduct;
}
