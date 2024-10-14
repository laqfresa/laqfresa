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
import org.lq.internal.domain.combo.ComboDTO;
import org.lq.internal.domain.combo.ComboUpdateDTO;
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.exception.ProblemException;
import org.lq.internal.service.ComboService;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComboApi {

    @Inject
    ComboService comboService;

    @GET
    @Path("/combo")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el listado de combos correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                    [
                                                        {
                                                            "idCombo": 1,
                                                            "idDetailCombo": 1,
                                                            "idTypeDiscount": 1,
                                                            "name": "DIA DE LAS MADRES",
                                                            "description": "DIA DE LAS MADRES",
                                                            "value": 5000,
                                                            "detailCombos": [
                                                                {
                                                                    "idDetailCombo": 1,
                                                                    "idProduct": 1
                                                                }
                                                            ]
                                                        }
                                                    ]"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de combos en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registros de combos."
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
            summary = "Obtener listado de combos",
            description = "Se obtiene el listado con la información de los combos registrados"
    )
    public Response getCombo() {
        return Response.ok().entity(comboService.getCombo()).build();
    }

    @GET
    @Path("/combosActive")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el listado de combos activos correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                [
                                                    {
                                                        "comboId": 1,
                                                        "comboName": "Combo Activo 1",
                                                        "details": [
                                                            {
                                                                "detailId": 1,
                                                                "name": "Detalle Activo 1"
                                                            },
                                                            {
                                                                "detailId": 2,
                                                                "name": "Detalle Activo 2"
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "comboId": 2,
                                                        "comboName": "Combo Activo 2",
                                                        "details": [
                                                            {
                                                                "detailId": 3,
                                                                "name": "Detalle Activo 3"
                                                            }
                                                        ]
                                                    }
                                                ]"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de combos activos en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registros de combos activos."
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
            summary = "Obtener listado de combos activos",
            description = "Se obtiene el listado con la información de los combos activos registrados"
    )
    public Response getComboActive() {
        return Response.ok().entity(comboService.getComboActive()).build();
    }

    @POST
    @Transactional
    @Path("/combo/register")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Combo registrado exitosamente."
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
                            description = "El combo ya existe.",
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
            summary = "Registro de combo",
            description = "Registra un nuevo combo en la aplicación"
    )
    public Response saveCombo(
            @Valid ComboDTO comboDTO
    ) {
        comboService.saveCombo(comboDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/combo/update")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Combo actualizado exitosamente."
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
                            description = "No se encontró el combo a actualizar.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "Combo no encontrado."
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
            summary = "Actualización de combo",
            description = "Actualiza un combo en la aplicación"
    )
    public Response updateIngredient(
            @Valid ComboUpdateDTO comboUpdateDTO
    ) {
        comboService.updateCombo(comboUpdateDTO);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Transactional
    @Path("/combo/delete/{idCombo}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Combo desactivado exitosamente."
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errores de validación de entrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            example = """
                                                {
                                                  "El ID del combo debe ser un número positivo."
                                                }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontró el combo a desactivar.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "Combo no encontrado."
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
            summary = "Desactivación de combo",
            description = "Desactiva un combo en la aplicación"
    )
    public Response deleteIngredient(
            @PathParam("idCombo")
            @Positive(message = "El id del combo debe ser un número positivo.")
            Long idCombo
    ) {
        comboService.deactivateCombo(idCombo);
        return Response.status(Response.Status.OK).build();
    }
}
