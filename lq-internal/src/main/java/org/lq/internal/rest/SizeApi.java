package org.lq.internal.rest;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
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
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.exception.ProblemException;
import org.lq.internal.service.SizeService;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SizeApi {

    @Inject
    SizeService sizeService;

    @GET
    @Path("/sizes")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el listado de productos correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                    [
                                                        {
                                                            "prdLvlNumber": "1",
                                                            "name": "Fresas con Crema",
                                                            "description": "Deliciosas fresas frescas acompañadas de crema batida",
                                                            "value": 10000
                                                        },
                                                        {
                                                            "prdLvlNumber": "2",
                                                            "name": "Fresas con Chocolate",
                                                            "description": "Fresas frescas bañadas en chocolate derretido",
                                                            "value": 12000
                                                        },
                                                        {
                                                            "prdLvlNumber": "3",
                                                            "name": "Tazón de Fresas",
                                                            "description": "Tazón relleno de fresas frescas cortadas",
                                                            "value": 8000
                                                        }
                                                    ]"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de productos en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registro de productos."
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
            summary = "Obtener listado de productos",
            description = "Se obtiene el listado con la información de los productos registrados"
    )
    public Response getSize(){
        return Response.ok().entity(sizeService.getSizes()).build();
    }

    @GET
    @Path("/size/{numberSize}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el producto por número correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                    {
                                                        "prdLvlNumber": "2",
                                                        "name": "Fresas con Chocolate",
                                                        "description": "Fresas frescas bañadas en chocolate derretido",
                                                        "value": 12000
                                                    }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de productos en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registro de productos con el número ingresado."
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
            summary = "Obtener producto con el número ingresado",
            description = "Se obtiene el producto con el número ingresado"
    )
    public Response getProductNumber(
            @NotNull(message = "El id de tamaño no puede ser menor a cero")
            @PathParam("numberSize") long numberSize
    ){
        return Response.ok().entity(sizeService.getSizeNumber(numberSize)).build();
    }

}
