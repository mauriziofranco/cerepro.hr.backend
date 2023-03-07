package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Assert;
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
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.SurveyQuestion;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.question.QuestionRepository;
import centauri.academy.cerepro.persistence.repository.surveyquestion.SurveyQuestionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyQuestionControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(SurveyQuestionControllerTest.class);

	@Spy
	private SurveyQuestionController sqController;
	@Mock
	private SurveyQuestionRepository sqRepository;
	@Mock
	private SurveyRepository sRepository;
	@Mock
	private QuestionRepository qRepository;

	@Before
	public void setup() {
		sqController = new SurveyQuestionController();
		ReflectionTestUtils.setField(sqController, "sqRepository", sqRepository);
		ReflectionTestUtils.setField(sqController, "sRepository", sRepository);
		ReflectionTestUtils.setField(sqController, "qRepository", qRepository);
	}

	@Test
	public void testListAllUsers() {
		List<SurveyQuestion> roleList = new ArrayList<SurveyQuestion>();
		roleList.add(new SurveyQuestion());
		when(this.sqRepository.findAll()).thenReturn(roleList);
		ResponseEntity<List<SurveyQuestion>> responseEntity = this.sqController.listAllSurveyQuestion();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}

	
	@Test
	public void testInsertSurveyQuestionSuccessfully() {
		SurveyQuestion testSQ = new SurveyQuestion (100L,4L, 40L, 5L);
		Survey testSurvey = new Survey(4L, "ciao", 20L, "ciao ciao ciao");
		Question testQuestion = new Question (40L, "test", "tester", "ansa test", true) ;

		Optional<Survey> survey = Optional.of(testSurvey);
		Optional<Question> question = Optional.of(testQuestion);
	
		when(sqRepository.findBySurveyIdAndQuestionId(4L, 40L)).thenReturn(null);
		when(sRepository.findById(4L)).thenReturn(survey);
		when(qRepository.findById(40L)).thenReturn(question);
		when(this.sqRepository.save(testSQ)).thenReturn(testSQ);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.sqController.createSQ(testSQ);
		
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals(4, ((SurveyQuestion)responseEntity.getBody()).getSurveyId().longValue());
		Assert.assertEquals(40, ((SurveyQuestion)responseEntity.getBody()).getQuestionId().longValue());
		Assert.assertEquals(5, ((SurveyQuestion)responseEntity.getBody()).getPosition().longValue());
	}
	@Test
	public void testGetSurveyQuestionById() {
		SurveyQuestion testSQ = new SurveyQuestion(100L,4L, 40L, 5L) ;
		Optional<SurveyQuestion> currOpt = Optional.of(testSQ) ;
		when(this.sqRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.sqController.getSurveyQuestionById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(4, ((SurveyQuestion)responseEntity.getBody()).getSurveyId().longValue());
		Assert.assertEquals(40, ((SurveyQuestion)responseEntity.getBody()).getQuestionId().longValue());
		Assert.assertEquals(5, ((SurveyQuestion)responseEntity.getBody()).getPosition().longValue());
	}
	
	@Test
	public void testUpdateSurveyQuestionSuccessfully() {
		SurveyQuestion testSQ = new SurveyQuestion (100L,4L, 40L, 5L) ;
		Optional<SurveyQuestion> currOpt = Optional.of(testSQ) ;
		when(this.sqRepository.findById(100L)).thenReturn(currOpt);
		testSQ.setPosition(20L);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.sqController.updateSQ(100L, testSQ);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(20L, ((SurveyQuestion)responseEntity.getBody()).getPosition().longValue());
	}
	
	
	@Test
	public void testDeleteSurveyQuestionSuccessfully() {
		SurveyQuestion testSQ = new SurveyQuestion (100L,4L, 40L, 5L) ;
		Optional<SurveyQuestion> currOpt = Optional.of(testSQ) ;
		when(this.sqRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.sqController.deleteSurveyQuestion(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	
	@After
	public void teardown() {
		sqController = null;
	}
}
