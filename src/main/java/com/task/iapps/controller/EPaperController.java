package com.task.iapps.controller;

import com.task.iapps.dto.EPaperDTO;
import com.task.iapps.service.EPaperService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log
@RequestMapping("/epaper")
public class EPaperController {

    @Autowired
    EPaperService ePaperService;

    @PostMapping("/uploadxml")
    public ResponseEntity<Object> uploadXMLFile(@RequestParam MultipartFile xmlFile) {
        log.info("XML file upload Start:" + xmlFile.getName());
        try {
            EPaperDTO epaperDto = ePaperService.processXML(xmlFile);
            if(epaperDto != null){
                log.info("XML saved Successfully");
                return ResponseEntity.status(HttpStatus.OK).body(epaperDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("XML not saved");
    }

    @GetMapping("/getallepaper")
    public ResponseEntity<Object> getAllEPaperList(
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "order", defaultValue = "true", required = false) Boolean order,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        log.info("Getting all epaper");
        try {
            Page<EPaperDTO> pages = ePaperService.getAllEPaperList(search, sortBy, order, pageNumber, pageSize);
            if(pages != null){
                return ResponseEntity.status(HttpStatus.OK).body(pages);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to fetch records");
    }

}
