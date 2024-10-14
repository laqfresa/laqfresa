package org.lq.internal.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.lq.internal.domain.product.ProductDTO;
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.exception.ProblemException;
import org.lq.internal.service.ProductService;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductApi {

    @Inject
    ProductService productService;

    @GET
    @Path("/products")
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
    public Response getProducts() {
        return Response.ok().entity(productService.getProducts()).build();
    }

    @GET
    @Path("/product/{numberProduct}")
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
            @NotNull(message = "El número de producto no puede ser menor a cero")
            @PathParam("numberProduct") String numberProduct
    ){
        return Response.ok().entity(productService.getProductNumber(Long.parseLong(numberProduct))).build();
    }

    @POST
    @Transactional
    @Path("/product")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se crea el producto correctamente"
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "No hay registros de productos en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = """
                                                                        [
                                                                                "El campo description (descripción) no puede ser nulo o estar vacío.",
                                                                                "El campo prdLvlNumber (número producto) no puede ser nulo o estar vacío.",
                                                                                "El campo value (precio) no puede ser igual o menor a cero."
                                                                        ]
                                                                    """
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
            summary = "Guardar el producto",
            description = "Se guarda el producto de forma exitosa"
    )
    public Response saveProduct(
            @Valid ProductDTO productDTO
            ){
        productService.saveProduct(productDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/product")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se actualizo el producto correctamente"
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
                                                            example = """
                                                                        [
                                                                           "No se encontro producto para actualizar."
                                                                        ]
                                                                    """
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
            summary = "Actualizar el producto",
            description = "Se actualiza el producto de forma exitosa"
    )
    public Response updateProduct(
            @Valid ProductDTO productDTO
    ){
        productService.updateProduct(productDTO);
        return Response.status(Response.Status.OK
        ).build();
    }

    @PUT
    @Transactional
    @Path("/product/delete/{idProduct}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Producto desactivado exitosamente."
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errores de validación de entrada",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            example = """
                                                {
                                                  "El ID del producto debe ser un número positivo."
                                                }"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontró el producto a desactivar.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "Producto no encontrado."
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
            summary = "Desactivación de un producto",
            description = "Desactiva un producto en la aplicación"
    )
    public Response deleteIngredient(
            @PathParam("idProduct")
            @Positive(message = "El id del producto debe ser un número positivo.")
            Long idProduct
    ) {
        productService.deactivateProduct(idProduct);
        return Response.status(Response.Status.OK).build();
    }
}
