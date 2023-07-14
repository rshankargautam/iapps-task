package com.task.iapps;

import com.task.iapps.controller.EPaperController;
import com.task.iapps.dto.EPaperDTO;
import com.task.iapps.service.EPaperService;
import com.task.iapps.utils.EPaperUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.xml.sax.SAXException;

import java.io.IOException;

@SpringBootTest
class IappsTaskApplicationTests {

	@Autowired
	private EPaperService ePaperService;

	@Autowired
	private EPaperController ePaperController;

	@Autowired
	EPaperUtils ePaperUtils;


	@Test
	void contextLoads() {
	}

	@Test
	public void validSortData_ThenNotNull() throws Exception {
		Page<EPaperDTO> result = ePaperService.getAllEPaperList( "", "id", false, 0, 10);
		Assert.assertEquals(ResponseEntity.status(HttpStatus.OK).body(result), ePaperController.getAllEPaperList("", "id", false, 0, 10));
	}

	@Test
	public void validXml_ThenTrue() throws IOException, SAXException {
		Assert.assertTrue(ePaperUtils.isValidXMLTest("epaper-valid.xml", "epaper.xsd"));
	}

	@Test
	public void invalidXML_ThenFalse() throws IOException, SAXException {
		Assert.assertFalse(ePaperUtils.isValidXMLTest("epaper-invalid.xml", "epaper.xsd"));
	}
}
