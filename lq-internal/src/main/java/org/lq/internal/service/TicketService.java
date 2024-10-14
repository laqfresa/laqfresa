package org.lq.internal.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.lq.internal.domain.detailOrder.DetailOrder;
import org.lq.internal.domain.order.Order;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TicketService {

    private final Logger LOG = Logger.getLogger(ProductService.class);

    @Inject
    OrderService orderService;

    public byte[] printMerchandiseReceipt(long ticket) throws DocumentException {

        Order order = orderService.ordersPendingNumber(ticket);
        List<DetailOrder> orderList = order.getDetailOrders();

        return generateThermalReceipt(order, orderList);
    }

    public byte[] generateThermalReceipt(Order order, List<DetailOrder> orderList) throws DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        float width = 164f;
        Document document = new Document(new Rectangle(width, PageSize.A4.getHeight()), 5f, 5f, 5f, 5f);
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font fontAttributes = FontFactory.getFont("Helvetica", 7.0F, Font.NORMAL, BaseColor.BLACK);
        Font fontBlackTitles = FontFactory.getFont("Helvetica-Bold", 8.0F, Font.BOLD, BaseColor.BLACK);

        PdfPTable sectionHeader = createSectionHeader(fontBlackTitles, fontAttributes, order);
        PdfPTable sectionTableInformation = addTableInformationMerchandiseReceipt(fontBlackTitles, fontAttributes, orderList);

        document.add(sectionHeader);
        document.add(new Paragraph(" "));
        document.add(sectionTableInformation);

        document.close();

        float contentHeight = getContentHeight(sectionHeader, sectionTableInformation, fontAttributes, fontBlackTitles);

        Document finalDocument = new Document(new Rectangle(width, contentHeight + 40), 5f, 5f, 5f, 5f);
        PdfWriter finalWriter = PdfWriter.getInstance(finalDocument, byteArrayOutputStream);
        finalDocument.open();

        finalDocument.add(sectionHeader);
        finalDocument.add(new Paragraph(" "));
        finalDocument.add(sectionTableInformation);

        finalDocument.close();
        finalWriter.close();

        return byteArrayOutputStream.toByteArray();
    }

    private float getContentHeight(PdfPTable headerTable, PdfPTable infoTable, Font fontAttributes, Font fontBlackTitles) {
        float rowHeight = 12f;
        int numRows = headerTable.size() + infoTable.size();
        return rowHeight * numRows;
    }

    private PdfPTable createSectionHeader(Font fontBlackTitles, Font fondAttributes, Order order) {
        PdfPTable table = new PdfPTable(1);

        addStoreInformationLabel(table, "Número de orden: #" + order.getIdOrder(), fontBlackTitles);
        addStoreInformationLabel(table, "Fecha: " + order.getCreationDate(), fondAttributes);

        return table;
    }

    private PdfPTable addTableInformationMerchandiseReceipt(Font fontBlackTitles, Font fondAttributes, List<DetailOrder> orderList) {
        PdfPTable table = new PdfPTable(1);

        for (DetailOrder detailOrder : orderList) {
            addStoreInformationLabel(table, "Producto: ", fontBlackTitles);
            addStoreInformationLabel(table, detailOrder.getProduct().getName() + "  X(" + detailOrder.getQuantity() + ")", fondAttributes);
            addStoreInformationLabel(table, "Cliente: ", fontBlackTitles);
            addStoreInformationLabel(table, detailOrder.getNameCustomer().toUpperCase(), fondAttributes);

            String toppingsClasicos = detailOrder.getDetailAdditionals().stream()
                    .filter(additional -> "Toppings Clásicos".equals(additional.getIngredient().getIngredientType().getName()))
                    .map(additional -> additional.getIngredient().getName())
                    .collect(Collectors.joining(", "));
            addStoreInformationLabel(table, "Toppings Clásicos: ", fontBlackTitles);
            addStoreInformationLabel(table, toppingsClasicos.isEmpty() ? "N/A" : toppingsClasicos, fondAttributes);

            String toppingsPremiums = detailOrder.getDetailAdditionals().stream()
                    .filter(additional -> "Toppings Premium".equals(additional.getIngredient().getIngredientType().getName()))
                    .map(additional -> additional.getIngredient().getName())
                    .collect(Collectors.joining(", "));
            addStoreInformationLabel(table, "Toppings Premiums: ", fontBlackTitles);
            addStoreInformationLabel(table, toppingsPremiums.isEmpty() ? "N/A" : toppingsPremiums, fondAttributes);

            String salsas = detailOrder.getDetailAdditionals().stream()
                    .filter(additional -> "Salsas".equals(additional.getIngredient().getIngredientType().getName()))
                    .map(additional -> additional.getIngredient().getName())
                    .collect(Collectors.joining(", "));
            addStoreInformationLabel(table, "Salsas: ", fontBlackTitles);
            addStoreInformationLabel(table, salsas.isEmpty() ? "N/A" : salsas, fondAttributes);

            String iceCream = detailOrder.getDetailAdditionals().stream()
                    .filter(additional -> "HELADOS".equals(additional.getIngredient().getIngredientType().getName()))
                    .map(additional -> additional.getIngredient().getName())
                    .collect(Collectors.joining(", "));
            addStoreInformationLabel(table, "Helados: ", fontBlackTitles);
            addStoreInformationLabel(table, iceCream.isEmpty() ? "N/A" : iceCream, fondAttributes);

            String adicionales = detailOrder.getDetailAdditionals().stream()
                    .filter(additional -> "Adicionales".equals(additional.getIngredient().getIngredientType().getName()))
                    .map(additional -> additional.getIngredient().getName())
                    .collect(Collectors.joining(", "));
            addStoreInformationLabel(table, "Adicionales: ", fontBlackTitles);
            addStoreInformationLabel(table, adicionales.isEmpty() ? "N/A" : adicionales, fondAttributes);

            addStoreInformationLabel(table, "Observación: ", fontBlackTitles);
            addStoreInformationLabel(table, detailOrder.getObservation().toUpperCase(), fondAttributes);

            addStoreInformationLabel(table, "___________________________", fondAttributes);
            addStoreInformationLabel(table, " ", fondAttributes);
        }
        return table;
    }

    private void addStoreInformationLabel(PdfPTable table, String label, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(label, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPadding(2.0F);
        table.addCell(cell);
    }
}
