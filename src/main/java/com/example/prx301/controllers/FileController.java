package com.example.prx301.controllers;

import com.example.prx301.entitties.DB;
import com.example.prx301.entitties.Major;
import com.example.prx301.entitties.Student;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.utils.XMLConverter;
import com.example.prx301.utils.XMLHelpers;
import com.example.prx301.utils.XmlGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("api/v1/file")
public class FileController {
    private XMLHelpers helpers;
    private XMLConverter converter;
    private XmlGenerator xmlGenerator;

    public FileController(XMLHelpers helpers, XMLConverter converter, XmlGenerator xmlGenerator) {
        this.helpers = helpers;
        this.converter = converter;
        this.xmlGenerator = xmlGenerator;
    }

    @GetMapping
    public ResponseEntity<?> getXMLFile() {
        try {
            String s = xmlGenerator.generateDBXMLData();
            System.out.println("generated file path: " + s);
            Path filePath = Path.of(s);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = "application/xml";
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
            }
        } catch (MalformedURLException | TransformerException | XPathExpressionException | JAXBException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @GetMapping("/pdf")
    public ResponseEntity<?> exportToPdf() {
        try {
            String s = converter.convertToPDF();
            System.out.println("generated file path: " + s);
            Path filePath = Path.of(s);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("firstname: "+filePath.toString());
            if (resource.exists()) {
                String contentType = "application/pdf";
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
            }
        } catch (MalformedURLException ex) {
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public String fileUpload(@RequestParam("schema") MultipartFile schemaFile, @RequestParam("xml") MultipartFile xmlFIle, ModelMap model) throws IOException {
        String pathOfUploadXMl = "C:\\Users\\ADMIN\\Desktop\\New folder\\prx301\\src\\main\\resources\\storage\\xml\\";
        File xml = new File(pathOfUploadXMl + xmlFIle.getOriginalFilename());

        String pathOfUploadSchema = "C:\\Users\\ADMIN\\Desktop\\New folder\\prx301\\src\\main\\resources\\storage\\schema\\";
        File schema = new File(pathOfUploadSchema +schemaFile.getOriginalFilename());

        System.out.println("in upload file");


        String result =null;
        schema.createNewFile();
        xml.createNewFile();
        try{
            FileOutputStream fout = new FileOutputStream(schema);
            fout.write(schemaFile.getBytes());
            fout.close();
            fout = new FileOutputStream(xml);
            fout.write(xmlFIle.getBytes());
            fout.close();
             result = XMLHelpers.validateXml(schema, xml);
            model.addAttribute("xmlValidateResult", result);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @PutMapping
    public String importXMLFile(@RequestParam("xml") MultipartFile xmlFIle) throws IOException { ;
        String pathOfUploadXMl = "src/main/xml/importXML"+xmlFIle.getOriginalFilename();
        File xml = new File(pathOfUploadXMl);
        xml.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(xml);
        outputStream.write(xmlFIle.getBytes());
        outputStream.close();
        try {
            helpers.importXMLToDB(xml);
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (StudentException e) {
            e.printStackTrace();
        } catch (TransformerException transformerException) {
            transformerException.printStackTrace();
        }
        return "Import";
    }

    @PutMapping("/{auto}")
    public String autoImport(@PathVariable("auto") String auto) throws JAXBException { ;
        String pathOfUploadXMl = "src/main/xml/temp.xml";
        File xml = new File(pathOfUploadXMl);
        JAXBContext tempContext = JAXBContext.newInstance(DB.class);
        DB temp = (DB) tempContext.createUnmarshaller().unmarshal(xml);
        List<Student> students = temp.getStudents().getStudents();
        List<Major> majors = temp.getMajors().getMajors();
        File dbFile = new File("src/main/xml/xmlprx301.xml");
        DB myDB = (DB) tempContext.createUnmarshaller().unmarshal(dbFile);
        myDB.getStudents().getStudents().addAll(students);
        myDB.getMajors().getMajors().addAll(majors);
        tempContext.createMarshaller().marshal(myDB,dbFile);
        return "Import";
    }
}
