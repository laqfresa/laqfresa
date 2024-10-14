package org.lq.internal.domain.ingredient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IngredientUpdateDTO {

    @NotNull(message = "El ID del ingrediente no puede ser nulo.")
    private int ingredientId;

    @NotNull(message = "El tipo de ingrediente no puede ser nulo.")
    private int ingredientType;

    @NotEmpty(message = "El nombre del ingrediente no puede estar vacío.")
    @Size(max = 100, message = "El nombre del ingrediente no puede tener más de 100 caracteres.")
    private String name;

    private Boolean active;
}
