package com.example.prx301.repositories.implementations;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.errors.MajorValidationResult;
import com.example.prx301.exceptions.MajorException;
import com.example.prx301.repositories.MajorRepository;
import com.example.prx301.utils.DomHelper;
import com.example.prx301.utils.XMLHelpers;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.util.List;

@Repository
public class MajorRepositoryImpl implements MajorRepository<MajorDTO> {
    private Document document;
    private File xmlFile;

    public MajorRepositoryImpl(Document document, File xmlFile) {
        this.document = document;
        this.xmlFile = xmlFile;
    }


    @Override
    public MajorDTO createMajor(MajorDTO dto){
        Element majorElement = XMLHelpers.createMajorElement(dto, document);
        NodeList majors = document.getElementsByTagName("majors");
        majors.item(0).appendChild(majorElement);
        try {
            XMLHelpers.saveXMLContent(document,xmlFile);
        } catch (TransformerException transformerException) {
            transformerException.printStackTrace();
            return null;
        }
        return dto;
    }

    @Override
    public List<MajorDTO> loadMajor() {
        try {
            return DomHelper.loadXML(document).getMajors();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MajorDTO updateMajor(MajorDTO dto) throws MajorException {
        Element majorElement = XMLHelpers.createMajorElement(dto, document);
        NodeList majors = document.getElementsByTagName("major");
        Node replaceMajor = null;
        for (int i = 0; i < majors.getLength(); i++) {
            NodeList majorInfo = majors.item(i).getChildNodes();
            for (int j = 0; j < majorInfo.getLength(); j++) {
                Node majorID = majorInfo.item(j);
                String nodeName = majorID.getNodeName();
                if(nodeName.equals("majorId") && majorID.getTextContent().contains(dto.getId())){
                    replaceMajor = majorID.getParentNode();
                    replaceMajor.getParentNode().replaceChild(majorElement,replaceMajor);
                }
            }
        }
        if(replaceMajor == null){
            MajorValidationResult result = new MajorValidationResult("ID of major is not exist",null);
            throw new MajorException(result);
        }
        return dto;
    }

    @Override
    public boolean removeMajor(String majorId) throws MajorException {
        NodeList majors = document.getElementsByTagName("major");
        Node major = null;
        for (int i = 0; i < majors.getLength(); i++) {
            NodeList majorInfo = majors.item(i).getChildNodes();
            for (int j = 0; j < majorInfo.getLength(); j++) {
                Node tmp = majorInfo.item(j);
                String nodeName = tmp.getNodeName();
                if(nodeName.equals("majorId") && tmp.getTextContent().trim().equalsIgnoreCase(majorId.trim())){
                    major = tmp.getParentNode();
                    major.removeChild(tmp);
                }
            }
        }

        return major == null ? false:true;
    }
}
