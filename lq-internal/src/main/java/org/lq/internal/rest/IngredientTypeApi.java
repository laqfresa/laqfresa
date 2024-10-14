package org.lq.internal.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.lq.internal.domain.ingredient.IngredientTypeDTO;
import org.lq.internal.domain.ingredient.IngredientTypeUpdateDTO;
import org.lq.internal.domain.ingredient.TypeIngredient;
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.exception.ProblemException;
import org.lq.internal.service.IngredientService;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientTypeApi {

    @Inject
    IngredientService ingredientService;

    @GET
    @Path("/ingredientsType")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Lista de tipos de ingredientes obtenida exitosamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            implementation = TypeIngredient.class
                                    ),
                                    examples = @ExampleObject(
                                            value = """
                                                    [
                                                        {
                                                            "ingredientTypeId": 1,
                                                            "name": "Salsas",
                                                            "active": true
                                                        },
                                                        {
                                                            "ingredientTypeId": 2,
                                                            "name": "Capas",
                                                            "active": true
                                                        },
                                                        {
                                                            "ingredientTypeId": 3,
                                                            "name": "Toppings Premium",
                                                            "active": true
                                                        },
                                                        {
                                                            "ingredientTypeId": 4,
                                                            "name": "Toppings Clásicos",
                                                            "active": true
                                                        },
                                                        {
                                                            "ingredientTypeId": 5,
                                                            "name": "Adicionales",
                                                            "active": true
                                                        }
                                                    ]
                                                    """
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontraron tipos de ingredientes",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron tipos de ingredientes registrados"
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
                                    schema = @Schema(
                                            implementation = HandlerException.ResponseError.class
                                    )
                            )
                    )
            }
    )
    @Operation(
            summary = "Obtener tipos de ingredientes",
            description = "Devuelve una lista con todos los tipos de ingredientes registrados en el sistema"
    )
    public Response getTypeIngredients() {
        return Response.ok().entity(ingredientService.getIngredientType()).build();
    }

    @POST
    @Transactional
    @Path("/ingredientsType/register")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Tipo de ingrediente registrado exitosamente"
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errores de validación",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            example = """
                                                    {
                                                      "El campo name no puede ser nulo ni estar vacío."
                                                    }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = HandlerException.ResponseError.class
                                    )
                            )
                    )
            }
    )
    @Operation(
            summary = "Registrar tipo de ingrediente",
            description = "Registra un nuevo tipo de ingrediente en el sistema"
    )
    public Response saveIngredientType(
            @Valid IngredientTypeDTO ingredientTypeDTO
    ) {
        ingredientService.saveIngredientType(ingredientTypeDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/ingredientsType/update")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "204",
                            description = "Tipo de ingrediente actualizado exitosamente."
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
                            description = "Tipo de ingrediente no encontrado.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontró el tipo de ingrediente especificado."
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
            summary = "Actualizar tipo de ingrediente",
            description = "Actualiza los datos de un tipo de ingrediente existente"
    )
    public Response updateIngredientType(
            @Valid IngredientTypeUpdateDTO ingredientTypeDTO
    ) {
        ingredientService.updateIngredientType(ingredientTypeDTO);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Transactional
    @Path("/ingredientsType/{ingredientTypeId}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "204",
                            description = "Tipo de ingrediente eliminado exitosamente."
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "Tipo de ingrediente no encontrado.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontró el tipo de ingrediente especificado."
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
            summary = "Eliminar tipo de ingrediente",
            description = "Elimina lógicamente un tipo de ingrediente existente"
    )
    public Response deleteIngredientType(
            @PathParam("ingredientTypeId") Long ingredientTypeId
    ) {
        ingredientService.deleteIngredientType(ingredientTypeId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
