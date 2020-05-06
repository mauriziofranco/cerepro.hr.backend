package centauri.academy.cerepro.backend;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.SurveyInterview;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.InterviewRepository;
import centauri.academy.cerepro.persistence.repository.SurveyInterviewRepository;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;

@RestController
@RequestMapping("/api/v1/surveyinetrview")
public class SurveyInterviewController {
	
	public static final Logger logger = LoggerFactory.getLogger(SurveyInterviewController.class);
	@Autowired
	private SurveyInterviewRepository siRepository;
	@Autowired
	private SurveyRepository sRepository;
	@Autowired
	private InterviewRepository iRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<SurveyInterview>> listAllSurveyInterview() {
		List<SurveyInterview> si = siRepository.findAll();
		if (si.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(si, HttpStatus.OK);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createSI(@Valid @RequestBody final SurveyInterview si) {
		logger.info("Creating SurveyInterview : {}", si);

		  if (siRepository.findBySurveyIdAndInterviewId(si.getSurveyId(), si.getInterviewId()) != null) {
			logger.error("Unable to create. A SurveyInterview with surveyId {} already exist", si.getSurveyId());
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new surveyInterview. A SurveyInterview with surveyId "
							+ si.getSurveyId() + " and interviewId " + si.getInterviewId() + " already exist."),
					HttpStatus.CONFLICT);

		} 
		if (!(sRepository.findById(si.getSurveyId()).isPresent())) {
			logger.info(" Survey with id " + si.getSurveyId() + " is not found");
			return new ResponseEntity<>(new CustomErrorType(" Survey with id " + si.getSurveyId() + " is not found"),
					HttpStatus.CONFLICT);
		} 
		
		if (!(iRepository.findById(si.getInterviewId()).isPresent())) {
			logger.info(" Interview with id " + si.getInterviewId() + " is not found");
			return new ResponseEntity<>(
					new CustomErrorType(" Interview with id " + si.getInterviewId() + " is not found"),
					HttpStatus.CONFLICT);
		}
		siRepository.save(si);
		return new ResponseEntity<>(si, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getSurveyInterviewById(@PathVariable("id") final Long id) {
		Optional<SurveyInterview> si = siRepository.findById(id);
		if (!si.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("SurveyInterview with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(si.get(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateSI(@PathVariable("id") final Long id,
			@RequestBody SurveyInterview si) {

		Optional<SurveyInterview> optSI = siRepository.findById(id);
		if (!optSI.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to upate. SurveyInterview with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		SurveyInterview currentSI = optSI.get();
		currentSI.setSurveyId(si.getSurveyId());
		currentSI.setInterviewId(si.getInterviewId());
		currentSI.setPosition(si.getPosition());
		siRepository.saveAndFlush(currentSI);

		return new ResponseEntity<>(currentSI, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteSurveyInterview(@PathVariable("id") final Long id) {
		Optional<SurveyInterview> optSI = siRepository.findById(id);
		if (!optSI.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. SurveyInterview with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		siRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
