package com.example.prx301.controllers;

import com.example.prx301.utils.XMLHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;

@Controller
@SessionAttributes("validateResult")
public class FileController {
    @Autowired
    private ServletContext context;
    @Autowired
    private XMLHelpers helpers;
    private Path fileStorageLocation = Path.of("/");

    @ModelAttribute(name = "validateXMLResult")
    public void order(Model model) {
        model.addAttribute("validateResult","");
    }

    @GetMapping("api/v1/file")
    public ResponseEntity<?> getXMLFile() {
        try {
            String s = helpers.generateXMLData();
            System.out.println("generated file path: " + s);
            Path filePath = Path.of(s);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("firstname: "+filePath.toString());
            if (resource.exists()) {
                String contentType = "application/xml";
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
            }
        } catch (MalformedURLException ex) {
        }
        return null;
    }
    @PostMapping("api/v1/file")
    public String fileUpload(@RequestParam("schema") MultipartFile schemaFile, @RequestParam("xml") MultipartFile xmlFIle, ModelMap model) throws IOException {
        context.getContextPath();
        String pathOfUploadXMl = "D:\\FPTU\\XML\\prx301_1\\src\\main\\resources\\storage\\xml\\";
        File xml = new File(pathOfUploadXMl + xmlFIle.getOriginalFilename());

        String pathOfUploadSchema = "D:\\FPTU\\XML\\prx301_1\\src\\main\\resources\\storage\\schema\\";
        File schema = new File(pathOfUploadSchema +schemaFile.getOriginalFilename());

        System.out.println("in upload file");


        schema.createNewFile();
        xml.createNewFile();
        try{
            FileOutputStream fout = new FileOutputStream(schema);
            fout.write(schemaFile.getBytes());
            fout.close();
            fout = new FileOutputStream(xml);
            fout.write(xmlFIle.getBytes());
            fout.close();
            String result = XMLHelpers.validateXml(schema, xml);
            model.addAttribute("xmlValidateResult", result);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "home";
    }
}
