package com.task.iapps.service;

import com.task.iapps.dto.EPaperDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EPaperService {

    public Page<EPaperDTO> getAllEPaperList(String search, String sortBy, Boolean order, Integer pageNumber, Integer pageSize) throws Exception;

    public EPaperDTO processXML(MultipartFile xmlFile) throws JAXBException, IOException, Exception;

}
