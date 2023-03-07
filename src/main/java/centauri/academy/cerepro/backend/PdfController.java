package centauri.academy.cerepro.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.service.PdfService;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfController {
	
	public static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	PdfService pdfService;
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<CeReProAbstractEntity> createPdfForSurveyFromId(@PathVariable("id") Long id){
		
		logger.debug("################### CREATING PDF METHOD STARTED ###################");
		return pdfService.generatePdf(id);
	}
		
}
