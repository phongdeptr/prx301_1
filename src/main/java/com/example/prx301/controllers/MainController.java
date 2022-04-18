package com.example.prx301.controllers;

import com.example.prx301.dto.DB;
import com.example.prx301.utils.DomHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

@Controller("/")
public class MainController {
    @GetMapping
    public String home(){
        return "redirect:students";
    }
}
