package io.exiled.salesbook.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.exiled.salesbook.model.Sale;
import io.exiled.salesbook.repos.SaleRepo;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service("export")
public class ExportService {
    private SaleRepo saleRepo;

    public ExportService(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }

    public void createFile() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);

            document.add(chunk);
            document.close();
        } catch (Exception e) {
            System.err.println("Whops");
            ;
        }
    }

    public void createTable() {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("FazeWay\n", font);

            document.add(chunk);

            PdfPTable table = new PdfPTable(6);
            addTableHeader(table);
            addRows(table);
//            addCustomRows(table);

            document.add(table);
            document.close();
        } catch (Exception e) {
            System.err.println("Whops " + e.getMessage());
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("No:", "Name:", "Email:","Price:","Description:","Category")
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
        for (int i = 0;i < col.size(); i++) {
            table.addCell("" + (i+1));
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

    private List<Sale> getRecords(){
        return saleRepo.findAll();
    }
}
