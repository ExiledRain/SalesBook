package io.exiled.salesbook.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.text.Font;
import io.exiled.salesbook.model.Sale;
import io.exiled.salesbook.repos.SaleRepo;
import io.exiled.salesbook.service.ExportService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Implementation of {@link io.exiled.salesbook.service.ExportService} interface.
 *
 * @author Exiled Rain
 * @version 1.0
 */

@Service("export")
public class ExportServiceImpl implements ExportService {
    private SaleRepo saleRepo;
    private String baseDir;
    private String fileName = "/dumb.pdf";

    private String destination = baseDir + fileName;

    public ExportServiceImpl(SaleRepo saleRepo) {
        setBaseDir();
        this.saleRepo = saleRepo;
    }

    public void manipulatePdf() throws Exception {
        File file = new File(destination);
        file.getParentFile().mkdirs();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destination));
        Document doc = new Document(pdfDoc);
        float[] columnWidths = {1, 5, 5, 5, 5, 7};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        PdfFont f = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Cell cell = new Cell(1, 6)
                .add(new Paragraph("FazeWay"))
                .setFont(f)
                .setFontSize(12)
                .setFontColor(DeviceGray.BLACK)
                .setBackgroundColor(new DeviceRgb(20, 212, 255))
                .setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(cell);
        for (int i = 0; i < 2; i++) {
            Cell[] headerFooter = new Cell[]{
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("#")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Name")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Email")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Category")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Price")),
                    new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Description"))
            };
            for (Cell hfCell : headerFooter) {
                if (i == 0) {
                    table.addHeaderCell(hfCell);
                } else {
                    table.addFooterCell(hfCell);
                }
            }
        }
        List<Sale> rec = getRecords();
        for (int counter = 0; counter < rec.size(); counter++) {
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(counter + 1))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(rec.get(counter).getName())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(rec.get(counter).getEmail())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(rec.get(counter).getCat())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(rec.get(counter).getTotalCost()))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.LEFT).add(new Paragraph(rec.get(counter).getDescription())));
        }
        doc.add(table);
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);
        Paragraph preface = new Paragraph();
        int total = 0;
        for (Sale s : getRecords()
        ) {
            total += s.getTotalCost();
        }
        preface.add(new Paragraph("" +
                "Total cost of all deals is:\n" +
                total));
        doc.add(preface);

        doc.close();
    }

    public List<Sale> getRecords() {
        return saleRepo.findAll();
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    private void setBaseDir() {
        this.baseDir = System.getProperty("user.home") + "/export";
        this.destination = baseDir + fileName;
    }
}
