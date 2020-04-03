/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import ui.StockManagement;

/**
 *
 * @author Fernet
 */
public class PDFTester {

    public static void main(String[] args) {

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
            document.open();
            document.add(new Paragraph("MIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO"));

            document.addAuthor("Lokesh Gupta");
            document.addCreationDate();
            document.addCreator("HowToDoInJava.com");
            document.addTitle("Set Attribute Example");
            document.addSubject("An example to show how attributes can be added to pdf files.");

            PdfPTable table = new PdfPTable(3); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            //cell3.setUserBorderPadding(true);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            document.add(table);

            // Apri il pdf appena creato
            Desktop.getDesktop().open(new File("HelloWorld.pdf"));

            document.close();
            writer.close();
        } catch (DocumentException e) {
            Logger.getLogger("genlog").warning("DocumentException\n" + StockManagement.printStackTrace(e));

            
        } catch (FileNotFoundException e) {
            Logger.getLogger("genlog").warning("FileNotFoundException\n" + StockManagement.printStackTrace(e));

            
        } catch (IOException ex) {
            Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));

        }

    }

}
