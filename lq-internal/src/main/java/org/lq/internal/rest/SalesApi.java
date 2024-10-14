package org.lq.internal.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.lq.internal.domain.order.Order;
import org.lq.internal.helper.exception.HandlerException;
import org.lq.internal.helper.format.LocalDateTimeConverter;
import org.lq.internal.service.SalesService;

import java.sql.SQLException;
import java.time.LocalDate;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalesApi {

    @Inject
    SalesService salesService;

    @GET
    @Path("/totalSalesDaily")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene las ventas diarias",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
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
            summary = "Obtener ventas diarias",
            description = "Obtiene el total de ventas por el dia especificado."
    )
    public Response getTotalOrdersDaily(@QueryParam("date") String date) throws SQLException {
        return Response.status(Response.Status.OK).entity(salesService.getTotalOrdersDaily(date)).build();
    }

    @GET
    @Path("/totalSalesWeek")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtienen las ventas semanales",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
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
            summary = "Obtener ventas semanales",
            description = "Obtiene el total de ventas para la semana especificada."
    )
    public Response getTotalOrdersWeek(@QueryParam("date") String date) throws SQLException {
        return Response.status(Response.Status.OK).entity(salesService.getTotalOrdersWeek(date)).build();
    }

    @GET
    @Path("/totalSalesMonth")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtienen las ventas mensuales",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)
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
            summary = "Obtener ventas mensuales",
            description = "Obtiene el total de ventas para el mes especificado."
    )
    public Response getTotalOrdersMonth(@QueryParam("date") String date) throws SQLException {
        return Response.status(Response.Status.OK).entity(salesService.getTotalOrdersMonth(date)).build();
    }

    @GET
    @Path("/totalSalesByRange")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el total de ventas por rango de fechas",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class)
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
            summary = "Obtener ventas por rango de fechas",
            description = "Obtiene el total de ventas para el rango de fechas especificado."
    )
    public Response getTotalOrdersByRange(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) throws SQLException {
        return Response.status(Response.Status.OK).entity(salesService.getTotalOrdersByRange(startDate, endDate)).build();
    }


    @GET
    @Path("/totalSales")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se obtiene el total de ventas",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Order.class)
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
            summary = "Obtener total de ventas",
            description = "Obtiene el total acumulado de todas las ventas realizadas."
    )
    public Response getTotalOrders() throws SQLException {
        return Response.status(Response.Status.OK).entity(salesService.getTotalOrders()).build();
    }
}
