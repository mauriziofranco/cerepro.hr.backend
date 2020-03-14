package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.rest.request.SingleQuestionReplyRequest;
import centauri.academy.cerepro.rest.request.SurveyReplyRequest;
import centauri.academy.cerepro.service.SurveyReplyRequestService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyReplyRequestControllerTest {

	public static final Logger log = LoggerFactory.getLogger(SurveyReplyRequestControllerTest.class);
	
	@Mock
	private SurveyReplyRepository surveyReplyRepository;
	@Mock
	private SurveyRepository surveyRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private SurveyReplyRequestService surveyReplyRequestService;
	@Spy
	private SurveyReplyRequestController surveyReplyRequestController;
	
	@Before
	public void setup() {
		surveyReplyRequestController = new SurveyReplyRequestController();
		/* setta nellistanza del controllore dove ci sta il field che si chiama tra le "" l'istanza survreorep:  */
//		ReflectionTestUtils.setField(surveyReplyRequestController, "surveyReplyRequestController", surveyReplyRequestController);
		ReflectionTestUtils.setField(surveyReplyRequestController, "surveyReplyRepository", surveyReplyRepository);
		ReflectionTestUtils.setField(surveyReplyRequestController, "surveyRepository", surveyRepository);
		ReflectionTestUtils.setField(surveyReplyRequestController, "userRepository", userRepository);
		ReflectionTestUtils.setField(surveyReplyRequestController, "surveyReplyRequestService", surveyReplyRequestService);
	}
	
	
	@Test
	public void testInsertSurveyReplyBySurveyReplyRequestControllerSuccessfully() {
		log.info(" ### testInsertSurveyBySurveyReplyRequestControllerReplySuccessfully() - START ### ");
		SurveyReplyRequest testSurveyReplyRequest = new SurveyReplyRequest();
		testSurveyReplyRequest.setUserId(100L);
		testSurveyReplyRequest.setSurveyId(6L);
		testSurveyReplyRequest.setUserId(120L);
		testSurveyReplyRequest.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReplyRequest.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReplyRequest.setAnswers(new ArrayList<SingleQuestionReplyRequest>());

		when(this.surveyReplyRequestService.answersToString(testSurveyReplyRequest.getAnswers())).thenReturn("");
		when(this.surveyReplyRequestService.pointsCalculator(testSurveyReplyRequest.getAnswers())).thenReturn(new Long(10));
		when(this.surveyRepository.findById(testSurveyReplyRequest.getSurveyId())).thenReturn(Optional.of(new Survey()));
		when(this.userRepository.findById(testSurveyReplyRequest.getUserId())).thenReturn(Optional.of(new User()));
//		when(this.surveyReplyRepository.save(surveyReply)).thenReturn(testSurveyReply);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyRequestController.insertSurveyReply(testSurveyReplyRequest);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//		Assert.assertEquals(6, ((SurveyReply)responseEntity.getBody()).getSurveyId());
//		Assert.assertEquals(120, ((SurveyReply)responseEntity.getBody()).getUserId());
//		Assert.assertEquals(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)), ((SurveyReply)responseEntity.getBody()).getStarttime());
//		Assert.assertEquals(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)), ((SurveyReply)responseEntity.getBody()).getEndtime());
//		Assert.assertEquals("testAnswer", ((SurveyReply)responseEntity.getBody()).getAnswers());
//		Assert.assertEquals("testPDF", ((SurveyReply)responseEntity.getBody()).getPdffilename());
//		Assert.assertEquals("testPoints", ((SurveyReply)responseEntity.getBody()).getPoints());		
		log.info(" ### testInsertSurveyReplyBySurveyReplyRequestControllerSuccessfully() - END ### ");
	}
	
	@After
	public void teardown() {
		surveyReplyRequestController = null;
	}

}