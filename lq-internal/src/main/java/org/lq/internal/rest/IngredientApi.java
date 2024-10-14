package org.lq.internal.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.lq.internal.domain.ingredient.IngredientDTO;
import org.lq.internal.domain.ingredient.IngredientUpdateDTO;
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.exception.ProblemException;
import org.lq.internal.service.IngredientService;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientApi {

    @Inject
    IngredientService ingredientService;

    @GET
    @Path("/ingredients")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el listado de ingredientes correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                    [
                                                        {
                                                            "ingredientId": 1,
                                                            "ingredientType": {
                                                                "ingredientTypeId": 1,
                                                                "name": "Salsas"
                                                            },
                                                            "name": "Salsa de Fresa"
                                                        },
                                                        {
                                                            "ingredientId": 2,
                                                            "ingredientType": {
                                                                "ingredientTypeId": 1,
                                                                "name": "Salsas"
                                                            },
                                                            "name": "Salsa de Chocolate"
                                                        },
                                                        {
                                                            "ingredientId": 3,
                                                            "ingredientType": {
                                                                "ingredientTypeId": 1,
                                                                "name": "Salsas"
                                                            },
                                                            "name": "Salsa de Caramelo"
                                                        }
                                                    ]"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de ingredientes en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registros de ingredientes."
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Obtener listado de ingredientes",
            description = "Se obtiene el listado con la información de los ingredientes registrados"
    )
    public Response getIngredient() {
        return Response.ok().entity(ingredientService.getIngredient()).build();
    }

    @GET
    @Path("/ingredientsToppingsActive")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el listado de ingredientes correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                    [
                                                        {
                                                            "ingredientId": 1,
                                                            "ingredientType": {
                                                                "ingredientTypeId": 1,
                                                                "name": "Salsas"
                                                            },
                                                            "name": "Salsa de Fresa"
                                                        },
                                                        {
                                                            "ingredientId": 2,
                                                            "ingredientType": {
                                                                "ingredientTypeId": 1,
                                                                "name": "Salsas"
                                                            },
                                                            "name": "Salsa de Chocolate"
                                                        },
                                                        {
                                                            "ingredientId": 3,
                                                            "ingredientType": {
                                                                "ingredientTypeId": 1,
                                                                "name": "Salsas"
                                                            },
                                                            "name": "Salsa de Caramelo"
                                                        }
                                                    ]"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de ingredientes en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registros de ingredientes."
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Obtener listado de ingredientes",
            description = "Se obtiene el listado con la información de los ingredientes registrados"
    )
    public Response getIngredientToppings() {
        return Response.ok().entity(ingredientService.getIngredientToppings()).build();
    }

    @POST
    @Transactional
    @Path("/ingredients/register")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Ingrediente registrado exitosamente."
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errores de validación de entrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            example = """
                                                    {
                                                      "El campo name no puede ser nulo ni estar vacío.",
                                                      "El campo ingredientTypeId no puede ser nulo."
                                                    }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "409",
                            description = "El ingrediente ya existe.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ProblemException.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Registro de ingrediente",
            description = "Registra un nuevo ingrediente en la aplicación"
    )
    public Response saveIngredient(
            @Valid IngredientDTO ingredientDTO
    ) {
        ingredientService.saveIngredient(ingredientDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/ingredients/update")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Ingrediente actualizado exitosamente."
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errores de validación de entrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            example = """
                                                    {
                                                      "El campo name no puede ser nulo ni estar vacío.",
                                                      "El campo ingredientTypeId no puede ser nulo."
                                                    }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontró el ingrediente a actualizar.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "Ingrediente no encontrado."
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Actualización de ingrediente",
            description = "Actualiza un ingrediente en la aplicación"
    )
    public Response updateIngredient(
            @Valid IngredientUpdateDTO ingredientDTO
    ) {
        ingredientService.updateIngredient(ingredientDTO);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Transactional
    @Path("/ingredients/delete/{ingredientId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Ingrediente eliminado exitosamente."
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errores de validación de entrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            example = """
                                                    {
                                                      "El ID del ingrediente debe ser un número positivo."
                                                    }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontró el ingrediente a eliminar.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "Ingrediente no encontrado."
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Eliminación de ingrediente",
            description = "Elimina un ingrediente en la aplicación"
    )
    public Response deleteIngredient(
            @PathParam("ingredientId")
            @Positive(message = "El ID del ingrediente debe ser un número positivo.")
            Long ingredientId
    ) {
        ingredientService.deleteIngredient(ingredientId);
        return Response.status(Response.Status.OK).build();
    }
}
