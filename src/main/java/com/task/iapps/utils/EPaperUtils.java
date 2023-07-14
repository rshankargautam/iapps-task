package com.task.iapps.utils;

import com.task.iapps.domain.EPaper;
import com.task.iapps.dto.EPaperDTO;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

@Log
@Component
public class EPaperUtils {

    public boolean isNotValidXML(InputStream xmlFileInputStream, InputStream xsdlFileNameInputStream) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdlFileNameInputStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFileInputStream));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static EPaperDTO toEPaperDTO(EPaper ePaper) {
        return EPaperDTO.builder()
                .dpi(ePaper.getDpi())
                .filename(ePaper.getFilename())
                .height(ePaper.getHeight())
                .width(ePaper.getWidth())
                .newspaperName(ePaper.getNewspaperName())
                .uploadDate(ePaper.getUploadDate())
                .build();
    }

    public boolean isValidXMLTest(String xmlFileName, String xsdFileName) {
            InputStream xmlInputStream = getFileAsInputStream(xmlFileName);
            InputStream xsdInputStream = getFileAsInputStream(xsdFileName);
            return isNotValidXML(xmlInputStream , xsdInputStream);
    }

    public File getFile(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }

    public InputStream getFileAsInputStream(String fileName){
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
