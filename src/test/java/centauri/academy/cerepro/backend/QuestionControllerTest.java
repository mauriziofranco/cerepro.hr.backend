package centauri.academy.cerepro.backend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Question;
import centauri.academy.cerepro.persistence.entity.SurveyQuestion;
import centauri.academy.cerepro.persistence.repository.question.QuestionRepository;
import centauri.academy.cerepro.persistence.repository.surveyquestion.SurveyQuestionRepository;

/**
 * 
 * @author daniele piccinni
 *
 */
@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuestionControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(QuestionControllerTest.class);

	@Spy
	private QuestionController questionController;
	@Mock
	private QuestionRepository questionRepository;
	@Mock
	private SurveyQuestionRepository surveyQuestionRepository;

	@Before
	public void setup() {
		questionController = new QuestionController();
		ReflectionTestUtils.setField(questionController, "questionRepository", questionRepository);
		ReflectionTestUtils.setField(questionController, "surveyQuestionRepository", surveyQuestionRepository);
	}

	@Test
	public void getAllQuestions() {
		logger.info("Test-getAllQuestions() ---- Start");
		List<Question> questionList = new ArrayList<Question>();
		questionList.add(new Question());
		when(this.questionRepository.findAll()).thenReturn(questionList);
		ResponseEntity<List<Question>> responseEntity = this.questionController.listAllQuestions();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, responseEntity.getBody().size());
		logger.info("Test-getAllQuestions() ---- End");
	}
	
	@Test
	public void testGetQuestionById() {
		logger.info("Test-testGetQuestionById() ---- Start");
		Question testQuestion = new Question (100L, "test", "tester", "ansa test", true) ;
		Optional<Question> currOpt = Optional.of(testQuestion) ;
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.getQuestionById(100L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("tester", ((Question)responseEntity.getBody()).getDescription());
		assertEquals("test", ((Question)responseEntity.getBody()).getLabel());
		assertEquals("ansa test", ((Question)responseEntity.getBody()).getAnsa());
		assertEquals(true, ((Question)responseEntity.getBody()).getCansa());
		logger.info("Test-testGetQuestionById() ---- End");
	}
	
	@Test
	public void testCreateQuestion() {
		logger.info("Test-testCreateQuestion() ---- Start");
		Question testQuestion = new Question (100L, "test", "tester", "ansa test", true) ;
		when(this.questionRepository.save(testQuestion)).thenReturn(testQuestion);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.createQuestion(testQuestion);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("test", ((Question)responseEntity.getBody()).getLabel());
		assertEquals("tester", ((Question)responseEntity.getBody()).getDescription());
		assertEquals("ansa test", ((Question)responseEntity.getBody()).getAnsa());
		assertEquals(true, ((Question)responseEntity.getBody()).getCansa());
		logger.info("Test-testCreateQuestion() ---- End");
	}
	
	@Test
	public void testUpdateQuestion() {
		logger.info("Test-testUpdateQuestion() ---- Start");
		Question testQuestion = new Question (100L, "test", "tester", "ansa test", true) ;
		Optional<Question> currOpt = Optional.of(testQuestion) ;
		List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		when(this.surveyQuestionRepository.findByQuestionId(100L)).thenReturn(surveyQuestionList);
		Question questionToUpdate = new Question(100L, "modifica", "desc test", "mod ansa", false);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.updateQuestion(100L, questionToUpdate);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("modifica", ((Question)responseEntity.getBody()).getLabel());
		assertEquals("desc test", ((Question)responseEntity.getBody()).getDescription());
		assertEquals("mod ansa", ((Question)responseEntity.getBody()).getAnsa());
		assertEquals(false, ((Question)responseEntity.getBody()).getCansa());
		logger.info("Test-testUpdateQuestion() ---- End");
	}
	
	@Test
	public void testUpdateInvalidQuestion() {
		logger.info("Test-testUpdateInvalidQuestion() ---- Start");
		Question testQuestion = new Question (100L, "test", "tester", "ansa test", true) ;
		Optional<Question> currOpt = Optional.of(testQuestion) ;
		List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();
		surveyQuestionList.add(new SurveyQuestion());
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		when(this.surveyQuestionRepository.findByQuestionId(100L)).thenReturn(surveyQuestionList);
		Question questionToUpdate = new Question(100L, "modifica", "desc test", "mod ansa", false);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.updateQuestion(100L, questionToUpdate);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("Test-testUpdateInvalidQuestion() ---- End");
	}
	
	@Test
	public void testUpdateInexistentQuestion() {
		logger.info("Test-testUpdateInexistentQuestion() ---- Start");
		Optional<Question> currOpt = Optional.empty();
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		Question questionToUpdate = new Question(100L, "modifica", "desc test", "mod ansa", false);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.updateQuestion(100L, questionToUpdate);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		logger.info("Test-testUpdateInexistentQuestion() ---- End");
	}
	
	@Test
	public void testDeleteQuestion() {
		logger.info("Test-testDeleteQuestion() ---- Start");
		Question testQuestion = new Question (100L, "test", "tester", "ansa test", true) ;
		Optional<Question> currOpt = Optional.of(testQuestion) ;
		List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		when(this.surveyQuestionRepository.findByQuestionId(100L)).thenReturn(surveyQuestionList);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.deleteQuestion(100L);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		logger.info("Test-testDeleteQuestion() ---- End");
	}

	@Test
	public void testDeleteInvalidQuestion() {
		logger.info("Test-testDeleteInvalidQuestion() ---- Start");
		Question testQuestion = new Question (100L, "test", "tester", "ansa test", true) ;
		Optional<Question> currOpt = Optional.of(testQuestion) ;
		List<SurveyQuestion> surveyQuestionList = new ArrayList<SurveyQuestion>();
		surveyQuestionList.add(new SurveyQuestion());
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		when(this.surveyQuestionRepository.findByQuestionId(100L)).thenReturn(surveyQuestionList);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.deleteQuestion(100L);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("Test-testDeleteInvalidQuestion() ---- End");
	}
	
	@Test
	public void testDeleteInexistentQuestion() {
		logger.info("Test-testDeleteInexistentQuestion() ---- Start");
		Optional<Question> currOpt = Optional.empty() ;
		when(this.questionRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.questionController.deleteQuestion(100L);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		logger.info("Test-testDeleteInexistentQuestion() ---- End");
	}
	
	@After
	public void teardown() {
		questionController = null;
	}
}
