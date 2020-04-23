package io.exiled.salesbook.service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import io.exiled.salesbook.model.Sale;
import io.exiled.salesbook.repos.SaleRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Stream;
import java.io.File;

@Service("export")
public class ExportService {
//    public String DEST = "Exported/dumb.pdf";
    private SaleRepo saleRepo;

    public ExportService(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }

    public void manipulatePdf() throws Exception {
        String dest = "Exported/dumb.pdf";
        File file = new File(dest);
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//        Document doc = new Document(pdfDoc, PageSize.A4.rotate());
        Document doc = new Document(pdfDoc);

        float[] columnWidths = {1, 5, 5, 5,5,7};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));

        PdfFont f = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Cell cell = new Cell(1, 6)
                .add(new Paragraph("FazeWay"))
                .setFont(f)
                .setFontSize(12)
                .setFontColor(DeviceGray.BLACK)
                .setBackgroundColor(new DeviceRgb(20,212,255))
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
        // We add one empty line
//        addEmptyLine(preface, 1);
        // Lets write a big header
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

//    public void createFile() {
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
//
//            document.open();
//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//            Chunk chunk = new Chunk("Hello World", font);
//
//            document.add(chunk);
//            document.close();
//        } catch (Exception e) {
//            System.err.println("Whops");
//        }
//    }
//
//    public void createTable() {
//
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("Exported/dumb.pdf"));
//
//            document.open();
//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//            Chunk chunk = new Chunk("FazeWay\n", font);
//
//            document.add(chunk);
//
//            PdfPTable table = new PdfPTable(6);
//            addTableHeader(table);
//            addRows(table);
////            addCustomRows(table);
//
//            document.add(table);
//            document.close();
//        } catch (Exception e) {
//            System.err.println("Whops " + e.getMessage());
//        }
//    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("No:", "Name:", "Email:", "Price:", "Description:", "Category")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPadding(5.0f);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
//        table.addCell("row 1, col 1");
//        table.addCell("row 1, col 2");
//        table.addCell("row 1, col 3");
        List<Sale> col = saleRepo.findAll();
        for (int i = 0; i < col.size(); i++) {
            table.addCell("" + (i + 1));
            table.addCell(col.get(i).getName());
            table.addCell(col.get(i).getEmail());
            table.addCell("" + col.get(i).getTotalCost());
            table.addCell(col.get(i).getDescription());
            table.addCell(col.get(i).getCat());
        }

    }
//    private void addCustomRows(PdfPTable table)
//            throws URISyntaxException, BadElementException, IOException {
//        Path path = Paths.get(ClassLoader.getSystemResource("logo.jpg").toURI());
//        Image img = Image.getInstance(path.toAbsolutePath().toString());
//        img.scalePercent(10);
//
//        PdfPCell imageCell = new PdfPCell(img);
//        table.addCell(imageCell);
//
//        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
//        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(horizontalAlignCell);
//
//        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
//        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
//        table.addCell(verticalAlignCell);
//    }

    private List<Sale> getRecords() {
        return saleRepo.findAll();
    }
}
