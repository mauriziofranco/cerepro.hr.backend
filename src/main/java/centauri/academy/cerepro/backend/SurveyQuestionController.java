package centauri.academy.cerepro.backend;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.SurveyQuestion;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.question.QuestionRepository;
import centauri.academy.cerepro.persistence.repository.surveyquestion.SurveyQuestionRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/surveyquestion")
public class SurveyQuestionController {

	public static final Logger logger = LoggerFactory.getLogger(SurveyQuestionController.class);
	@Autowired
	private SurveyQuestionRepository sqRepository;
	@Autowired
	private SurveyRepository sRepository;
	@Autowired
	private QuestionRepository qRepository;

	@GetMapping("/")
	public ResponseEntity<List<SurveyQuestion>> listAllSurveyQuestion() {
		List<SurveyQuestion> sq = sqRepository.findAll();
		if (sq.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sq, HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createSQ(@Valid @RequestBody final SurveyQuestion sq) {
		logger.info("Creating SurveyQuestion : {}", sq);

		  if (sqRepository.findBySurveyIdAndQuestionId(sq.getSurveyId(), sq.getQuestionId()) != null) {
			logger.error("Unable to create. A SurveyQuestion with surveyId {} already exist", sq.getSurveyId());
			return new ResponseEntity<>(
					new CustomErrorType("Unable to create new surveyQuestion. A SurveyQuestion with surveyId "
							+ sq.getSurveyId() + " and questionId " + sq.getQuestionId() + " already exist."),
					HttpStatus.CONFLICT);

		} 
		if (!(sRepository.findById(sq.getSurveyId()).isPresent())) {
			logger.info(" Survey with id " + sq.getSurveyId() + " is not found");
			return new ResponseEntity<>(new CustomErrorType(" Survey with id " + sq.getSurveyId() + " is not found"),
					HttpStatus.CONFLICT);
		} 
		
		if (!(qRepository.findById(sq.getQuestionId()).isPresent())) {
			logger.info(" Question with id " + sq.getQuestionId() + " is not found");
			return new ResponseEntity<>(
					new CustomErrorType(" Question with id " + sq.getQuestionId() + " is not found"),
					HttpStatus.CONFLICT);
		}
		sqRepository.save(sq);
		return new ResponseEntity<>(sq, HttpStatus.CREATED);
	}

	// method to get surveyQuestion by id
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getSurveyQuestionById(@PathVariable("id") final Long id) {
		Optional<SurveyQuestion> sq = sqRepository.findById(id);
		if (!sq.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("SurveyQuestion with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sq.get(), HttpStatus.OK);
	}

	// method to update an existing user
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateSQ(@PathVariable("id") final Long id,
			@RequestBody SurveyQuestion sq) {

		Optional<SurveyQuestion> optSQ = sqRepository.findById(id);
		if (!optSQ.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to upate. SurveyQuestion with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		SurveyQuestion currentSQ = optSQ.get();
		currentSQ.setSurveyId(sq.getSurveyId());
		currentSQ.setQuestionId(sq.getQuestionId());
		currentSQ.setPosition(sq.getPosition());
		sqRepository.saveAndFlush(currentSQ);

		return new ResponseEntity<>(currentSQ, HttpStatus.OK);
	}

	// delete an existing user
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteSurveyQuestion(@PathVariable("id") final Long id) {
		Optional<SurveyQuestion> optSQ = sqRepository.findById(id);
		if (!optSQ.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. SurveyQuestion with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		sqRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
