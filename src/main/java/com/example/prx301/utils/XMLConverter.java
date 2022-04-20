package com.example.prx301.utils;
import com.example.prx301.dto.DB;
import com.example.prx301.dto.StudentDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;


@Component
public class XMLConverter {
    private Document document;

    public XMLConverter(Document document) {
        this.document = document;
    }

    public String convertToPDF() throws XPathExpressionException, IOException, DocumentException, ParserConfigurationException, SAXException {
        DB db = DomHelper.loadXML(document);
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        PdfWriter.getInstance(doc, new FileOutputStream("src/pdf/output.pdf"));
        doc.setPageSize(PageSize.A3);
        doc.setMargins(1.0f,1.0f,1.0f,1.0f);
        doc.open();
        PdfPTable table = new PdfPTable(9);
        addTableHeader(table);
        table.setWidths(new float[] {10,14,14,44,16,10,20,10,13});
        db.getStudents().forEach((student) ->{
            addRows(table,student);
        });
        doc.add(table);
        doc.close();;
        return "src/pdf/output.pdf";
    }
    private void addTableHeader(PdfPTable table) {

        Stream.of("ID", "firstName", "lastName", "email", "Date of birth",
                "sex", "phone", "status", "majorName")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table, StudentDTO dto) {
        table.addCell(dto.getId());
        table.addCell(dto.getFirstName());
        table.addCell(dto.getLastName());
        table.addCell(dto.getEmail());
        table.addCell(dto.getDob());
        table.addCell(dto.isSex()?"Male":"Female");
        table.addCell(dto.getPhoneNumber());
        table.addCell(dto.getStatus());
        table.addCell(dto.getMajor().getName());
    }
}
