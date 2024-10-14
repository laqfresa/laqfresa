package org.lq.internal.rest;

import com.itextpdf.text.DocumentException;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.service.TicketService;

@Path("/internal")
public class TicketApi {

    @Inject
    TicketService ticketService;

    @GET
    @Path("/pdf/ticketOrder")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Se generó correctamente el ticket del pedido."
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Error en recursos suministrados",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(
                                    properties = {
                                            @SchemaProperty(
                                                    name = "errors",
                                                    example = """
                                                            [
                                                                "El número de la comanda (ticket) debe ser positivo."
                                                            ]
                                                            """
                                            )
                                    }))),
                    @APIResponse(
                            responseCode = "404",
                            description = "El número de la comanda no fue encontrado",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(
                                    properties = {
                                            @SchemaProperty(
                                                    name = "detail",
                                                    example = "No se encontraron comandas."
                                            )
                                    }))),
                    @APIResponse(
                            responseCode = "500",
                            description = "Error interno de servidor",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON
                            ))
            }
    )
    @Operation(
            summary = "Descargar comanda de pedido.",
            description = "Se genera la comanda del pedido"
    )
    public Response pdfMerchandiseReceipt(
            @QueryParam("ticket")
            @Positive(message = "El número de la comando (ticket) debe ser positivo.")
            @Parameter(name = "ticket",
                    required = true)
            long ticket
    ) throws PVException, DocumentException {
        return Response.status(Response.Status.OK)
                .entity(ticketService.printMerchandiseReceipt(ticket))
                .type("application/pdf")
                .header("Content-Disposition", "attachment; filename=comanda.pdf")
                .build();
    }
}
