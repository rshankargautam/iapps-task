package com.task.iapps.service;

import com.task.iapps.domain.EPaper;
import com.task.iapps.dto.EPaperDTO;
import com.task.iapps.repository.EPaperRepository;
import com.task.iapps.utils.EPaperUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
@Log
@Transactional
public class EPaperServiceImpl implements EPaperService {

    @Autowired
    EPaperRepository ePaperRepository;

    @Autowired
    EPaperUtils ePaperUtils;

    @Override
    public Page<EPaperDTO> getAllEPaperList(String search, String sortBy, Boolean order, Integer pageNumber, Integer pageSize) throws Exception {
        Pageable pageable = PageRequest.of
                (pageNumber == null ? 0 : pageNumber,
                        pageSize == null ? (Integer.MAX_VALUE - 1) : pageSize == Integer.MAX_VALUE ? (pageSize - 1) : pageSize,
                        Sort.by( order == null || order ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy == null ? "id" : sortBy));
        if(search.isEmpty()) {
            return ePaperRepository.findAll(pageable).map(EPaperUtils::toEPaperDTO);
        }
        else{
            return ePaperRepository.findByNewspaperName(search, pageable).map(EPaperUtils::toEPaperDTO);
        }
    }

    @Override
    public EPaperDTO processXML(MultipartFile xmlFile) throws IOException, Exception {

        log.info("XML processing started: ");

        ClassLoader classLoader = EPaperServiceImpl.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("epaper.xsd");
        if (validateXML(xmlFile.getInputStream(), inputStream)) {
            log.info("XML file validated:");

            EPaper ePaper = parseXML(xmlFile);
            if(ePaper != null ){
                return EPaperUtils.toEPaperDTO(ePaperRepository.save(ePaper));
            }
        } else{
            log.info("XML is not valid");
        }
        return null;
    }

    public boolean validateXML(InputStream xmlFileInputStream, InputStream xsdlFileNameInputStream) {

        return ePaperUtils.isNotValidXML(xmlFileInputStream, xsdlFileNameInputStream);
    }

    private EPaper parseXML(MultipartFile xmlFile) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(xmlFile.getInputStream());
            doc.getDocumentElement().normalize();

            EPaper ePaper = new EPaper();

            /*Extracting newspaperName*/
            NodeList newspaperNameList = doc.getElementsByTagName("newspaperName");
            if (newspaperNameList.getLength() > 0) {
                Element newspaperNameElement = (Element) newspaperNameList.item(0);
                String newspaperName = newspaperNameElement.getTextContent();
                ePaper.setNewspaperName(newspaperName);
            }

            /*Extracting attributes screenInfo */
            NodeList screenInfoList = doc.getElementsByTagName("screenInfo");
            if (screenInfoList.getLength() > 0) {
                Element screenInfoElement = (Element) screenInfoList.item(0);
                String width = screenInfoElement.getAttribute("width");
                String height = screenInfoElement.getAttribute("height");
                String dpi = screenInfoElement.getAttribute("dpi");

                ePaper.setWidth(Integer.parseInt(width));
                ePaper.setHeight(Integer.parseInt(height));
                ePaper.setDpi(Integer.parseInt(dpi));
            }
            if(ePaper != null){
                ePaper.setFilename(xmlFile.getOriginalFilename());
                ePaper.setUploadDate(new Date());
                return ePaper;
            }

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
