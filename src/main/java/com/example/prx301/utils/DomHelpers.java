package com.example.prx301.utils;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author Admin
 */
public class DomHelpers {

    public static Document getDocument(String path_to_file) {
        Document d = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            d = db.parse(path_to_file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return d = null;
        }
        return d;
    }


    public static void saveXMLContent(Document d, String path_to_file) throws TransformerException {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(d);
            StreamResult rs = new StreamResult(path_to_file);
            tf.transform(source, rs);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex.getMessage());
        }
    }
}