package org.lq.internal.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.lq.internal.domain.sales.Sales;
import org.lq.internal.domain.order.Order;
import org.lq.internal.domain.order.OrderDTO;
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.exception.ProblemException;
import org.lq.internal.service.OrderService;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderApi {

    @Inject
    OrderService orderService;

    @GET
    @Path("/orders")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el listado de pedidos correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            type = SchemaType.ARRAY,
                                            example = """
                                                    [
                                                    
                                                    ]"""
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No hay registros de pedidos en base de datos.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No se encontraron registro de pedidos."
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
            summary = "Obtener listado de pedidos",
            description = "Se obtiene el listado con la información de los pedidos registrados"
    )
    public Response getProducts() {
        return Response.ok().entity(orderService.getOrders()).build();
    }

    @POST
    @Transactional
    @Path("/order")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Pedido creado correctamente"
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "No se pudo crear el pedido debido a datos incorrectos o faltantes.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = """
                                                                        [
                                                                            "El idUser (id de usuario) no puede ser nulo o estar vacío.",
                                                                            "El nameCustomer (nombre del cliente) no puede ser nulo o estar vacío.",
                                                                            "El total (total del pedido) no puede ser nulo o estar vacío.",
                                                                            "El detailOrders (detalles del pedido) no puede ser nulo o estar vacío"
                                                                        ]
                                                                      """
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Crear pedido",
            description = "Crea un nuevo pedido con los datos proporcionados."
    )
    public Response createOrder(
            @RequestBody(
                    description = "Datos del pedido a crear",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = OrderDTO.class)
                    )
            )
            @Valid OrderDTO orderDTO
    ) {
        return Response.status(Response.Status.CREATED).entity(orderService.createOrder(orderDTO)).build();
    }

    @PUT
    @Transactional
    @Path("/order/{numberOrder}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Estado del pedido actualizado correctamente"
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "No se pudo actualizar el estado del pedido debido a datos incorrectos o faltantes.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = """
                                                                        [
                                                                            "El número de pedido no puede ser menor a cero."
                                                                        ]
                                                                      """
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Actualizar estado del pedido",
            description = "Actualiza el estado del pedido basado en el número de pedido proporcionado."
    )
    public Response updateOrder(
            @Parameter(
                    description = "Número de pedido a actualizar",
                    required = true
            )
            @NotNull(message = "El número de pedido no puede ser nulo")
            @Min(value = 1, message = "El número de pedido debe ser mayor a cero")
            @PathParam("numberOrder") long numberOrder
    ) {
        orderService.updateOrderStatus(numberOrder);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Transactional
    @Path("/cancelOrder/{numberOrder}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Pedido cancelado correctamente"
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "No se pudo cancelar el estado del pedido debido a datos incorrectos o faltantes.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = """
                                                                        [
                                                                            "El número de pedido no puede ser menor a cero."
                                                                        ]
                                                                      """
                                                    )
                                            }
                                    )
                            )
                    ),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = HandlerException.ResponseError.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Cancelar estado del pedido",
            description = "Cancela el estado del pedido basado en el número de pedido proporcionado."
    )
    public Response cancelOrder(
            @Parameter(
                    description = "Número de pedido a cancelar",
                    required = true
            )
            @NotNull(message = "El número de pedido no puede ser nulo")
            @Min(value = 1, message = "El número de pedido debe ser mayor a cero")
            @PathParam("numberOrder") long numberOrder
    ) {
        orderService.cancelOrderStatus(numberOrder);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/ordersPending")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Lista de pedidos pendientes obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontraron pedidos pendientes",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No hay pedidos pendientes en este momento."
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
            summary = "Obtener pedidos pendientes",
            description = "Obtiene la lista de pedidos que están pendientes de procesar."
    )
    public Response getPendingOrders() {
        return Response.status(Response.Status.OK).entity(orderService.ordersPending()).build();
    }

    @GET
    @Path("/ordersCompleted")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Lista de pedidos completados obtenida correctamente",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontraron pedidos completados",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No hay pedidos completados en este momento."
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
            summary = "Obtener pedidos completados",
            description = "Obtiene la lista de pedidos completados."
    )
    public Response getCompleted() {
        return Response.status(Response.Status.OK).entity(orderService.ordersCompleted()).build();
    }

    @POST
    @Transactional
    @Path("/ordersPending/{numberOrder}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Lista de pedidos pendientes obtenida correctamente por número",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontraron pedidos pendientes",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No hay pedidos pendientes en este momento por ese número."
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
            summary = "Obtener pedidos pendientes por número",
            description = "Obtiene la lista de pedidos por número que están pendientes de procesar."
    )
    public Response getPendingOrdersNumber(
            @Parameter(
            description = "Número de pedido a actualizar",
            required = true )
            @NotNull(message = "El número de pedido no puede ser nulo")
            @Min(value = 1, message = "El número de pedido debe ser mayor a cero")
            @PathParam("numberOrder") long numberOrder
    ) {
        return Response.status(Response.Status.OK).entity(orderService.ordersPendingNumber(numberOrder)).build();
    }

    @GET
    @Path("/ordersProgress/{numberOrder}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Obtiene pedido procesado correctamente por número",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No se encontraron pedidos procesando",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(
                                            implementation = ProblemException.class,
                                            properties = {
                                                    @SchemaProperty(
                                                            name = "detail",
                                                            example = "No hay pedidos que esten en proceso en este momento por ese número."
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
            summary = "Obtener pedidos en proceso por número",
            description = "Obtiene la lista de pedidos por número que están en proceso."
    )
    public Response getProgressOrdersNumber(
            @Parameter(
                    description = "Número de pedido a buscar",
                    required = true )
            @NotNull(message = "El número de pedido no puede ser nulo")
            @Min(value = 1, message = "El número de pedido debe ser mayor a cero")
            @PathParam("numberOrder") long numberOrder
    ) {
        return Response.status(Response.Status.OK).entity(orderService.ordersProgressNumber(numberOrder)).build();
    }
}
