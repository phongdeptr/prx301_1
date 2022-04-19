package com.example.prx301.repositories.implementations;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.repositories.MajorRepository;
import com.example.prx301.utils.DomHelper;
import com.example.prx301.utils.XMLHelpers;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.util.List;

@Repository
public class MajorRepositoryImpl implements MajorRepository {
    private Document document;
    private File xmlFile;

    public MajorRepositoryImpl(Document document, File xmlFile) {
        this.document = document;
        this.xmlFile = xmlFile;
    }

    @Override
    public Object createMajor(Object dto) {
        return null;
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
    public Object updateMajor(Object dto) {
        return null;
    }

    @Override
    public Object removeMajor(String majorId) {
        return null;
    }
}
