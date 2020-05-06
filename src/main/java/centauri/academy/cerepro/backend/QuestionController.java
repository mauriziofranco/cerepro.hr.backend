package centauri.academy.cerepro.backend;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
import centauri.academy.cerepro.persistence.entity.Question;
import centauri.academy.cerepro.persistence.entity.SurveyQuestion;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.QuestionRepository;
import centauri.academy.cerepro.persistence.repository.surveyquestion.SurveyQuestionRepository;

/**
 * controller class for question entity
 * 
 * @author daniele piccinni
 * @author Orlando Platì
 */

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
	public static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private SurveyQuestionRepository surveyQuestionRepository;

	// method to get list of questions
	@GetMapping("/")
	public ResponseEntity<List<Question>> listAllQuestions() {
		List<Question> questions = questionRepository.findAll();
		if (questions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // code 204
		}
		return new ResponseEntity<>(questions, HttpStatus.OK); // code 200
	}

	// method to get question by id
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getQuestionById(@PathVariable("id") final Long id) {
		Optional<Question> optQ = questionRepository.findById(id);
		if (!optQ.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Question with id " + id + " not found"),
					HttpStatus.NOT_FOUND); // code 404
		}
		return new ResponseEntity<>(optQ.get(), HttpStatus.OK); // code 200
	}

	// method to create a question
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createQuestion(@Valid @RequestBody final Question question) {
		logger.info("Creating question : {}", question);
		Question currentQuestion = validateQuestion(new Question(), question);
		questionRepository.save(currentQuestion);
		return new ResponseEntity<>(question, HttpStatus.CREATED); //code 201
	}

	// method to update an existing question
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateQuestion(@PathVariable("id") final Long id,
			@RequestBody Question question) {
		// fetch question based on id and set it to currentQuestion object of type
		// QuestionDTO
		Optional<Question> optQuestion = questionRepository.findById(id);
		if (!optQuestion.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to upate. Question with id " + id + " not found."),
					HttpStatus.NOT_FOUND); // code 404
		} else {
			List<SurveyQuestion> sqs = surveyQuestionRepository.findByQuestionId(id);
			if (sqs.isEmpty()) {
				Question currentQuestion = optQuestion.get();
				// update currentQuestion object data with question object data
				currentQuestion = validateQuestion(currentQuestion, question);
				// save currentQuestion obejct
				questionRepository.saveAndFlush(currentQuestion);
				// return ResponseEntity object
				return new ResponseEntity<>(currentQuestion, HttpStatus.OK); // code 200
			} else {
				return new ResponseEntity<>(
						new CustomErrorType(
								"Unable to upate. Question with id " + id + " is referenced by a survey question."),
						HttpStatus.CONFLICT); // code 409
			}
		}
	}

	// delete an existing question
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteQuestion(@PathVariable("id") final Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if (!question.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Question with id " + id + " not found."),
					HttpStatus.NOT_FOUND); // code 404
		}
		List<SurveyQuestion> sqs = surveyQuestionRepository.findByQuestionId(id);
		if (sqs.isEmpty()) {
			questionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // code 204
		} else {
			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to delete. Question with id " + id + " is referenced by a survey question."),
					HttpStatus.CONFLICT); // code 409
		}
	}

	/**
	 * 
	 * @param currentQuestion is the question that will be returned with updated values
	 * @param question is where values are taken to update currentQuestion
	 * 
	 */
	private Question validateQuestion(Question currentQuestion, Question question) {
		currentQuestion.setLabel(question.getLabel());
		currentQuestion.setDescription(question.getDescription());
		currentQuestion.setFullAnswer(question.getFullAnswer());

		// Answer A
		if (!question.getAnsa().equals("")) {
			currentQuestion.setAnsa(question.getAnsa());
			currentQuestion.setCansa(question.getCansa());
		}

		// Answer B
		if (question.getAnsb()!=null &&
				!question.getAnsb().equals("")) {
			currentQuestion.setAnsb(question.getAnsb());
			currentQuestion.setCansb(question.getCansb());
		}

		// Answer C
		if (question.getAnsc()!=null &&
				!question.getAnsc().equals("")) {
			currentQuestion.setAnsc(question.getAnsc());
			currentQuestion.setCansc(question.getCansc());
		}

		// Answer D
		if (question.getAnsd()!=null &&
				!question.getAnsd().equals("")) {
			currentQuestion.setAnsd(question.getAnsd());
			currentQuestion.setCansd(question.getCansd());
		}

		// Answer E
		if (question.getAnse()!=null &&
				!question.getAnse().equals("")) {
			currentQuestion.setAnse(question.getAnse());
			currentQuestion.setCanse(question.getCanse());
		}

		// Answer F
		if (question.getAnsf()!=null &&
				!question.getAnsf().equals("")) {
			currentQuestion.setAnsf(question.getAnsf());
			currentQuestion.setCansf(question.getCansf());
		}

		// Answer G
		if (question.getAnsg()!=null &&
				!question.getAnsg().equals("")) {
			currentQuestion.setAnsg(question.getAnsg());
			currentQuestion.setCansg(question.getCansg());
		}

		// Answer H
		if (question.getAnsh()!=null &&
				!question.getAnsh().equals("")) {
			currentQuestion.setAnsh(question.getAnsh());
			currentQuestion.setCansh(question.getCansh());
		}

		return currentQuestion;
	}
}