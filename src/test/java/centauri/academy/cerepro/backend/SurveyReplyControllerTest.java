package centauri.academy.cerepro.backend;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.service.SurveyReplyService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyReplyControllerTest {

	public static final Logger log = LoggerFactory.getLogger(SurveyReplyControllerTest.class);
	
	@Spy
	private SurveyReplyController surveyReplyController;
	@Spy
	private SurveyReplyService surveyReplyService;
	@Mock
	private SurveyReplyRepository surveyReplyRepository;
	@Mock
	private SurveyRepository surveyRepository;
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void setup() {
		surveyReplyController = new SurveyReplyController();
		/* setta nellistanza del controllore dove ci sta il field che si chiama tra le "" l'istanza survreorep:  */
		ReflectionTestUtils.setField(surveyReplyController, "surveyReplyRepository", surveyReplyRepository);
		ReflectionTestUtils.setField(surveyReplyController, "surveyRepository", surveyRepository);
		ReflectionTestUtils.setField(surveyReplyController, "userRepository", userRepository);
		ReflectionTestUtils.setField(surveyReplyController, "surveyReplyService", surveyReplyService);
		ReflectionTestUtils.setField(surveyReplyService, "surveyReplyService", surveyReplyService);	
		ReflectionTestUtils.setField(surveyReplyService, "surveyReplyRepository", surveyReplyRepository);
	}
	
	@Test
	public void testGetAllSurveyReply() {
		log.info(" ### testGetAllSurveyReply() - START ### ");
		List<SurveyReply> surveyReplyList = new ArrayList<SurveyReply>();
		surveyReplyList.add(new SurveyReply());
		when(this.surveyReplyRepository.findAll()).thenReturn(surveyReplyList); // quando evoco find all non lo faccio davvero, ma gli dico già cosa dovrà retituire 
		ResponseEntity<List<SurveyReply>> responseEntity = this.surveyReplyController.listAllSurveyReplies();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); //  verifica che la chiamata del controllore torni uno status http pari a 200
		Assert.assertEquals(1, responseEntity.getBody().size()); // verifica che la dim della lista restituita sia pari a 1	
		log.info(" ### testGetAllSurveyReply() - END ### ");
	}
	
	@Test
	public void testGetSurveyReplyById() {
		log.info(" ### testGetSurveyReplyById() - START ### ");
		SurveyReply testSurveyReply = new SurveyReply();
		testSurveyReply.setId(100L);
		testSurveyReply.setSurveyId(6L);
		testSurveyReply.setUserId(120L);
		testSurveyReply.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setAnswers("testAnswer");
		testSurveyReply.setPdffilename("testPDF");
		testSurveyReply.setPoints("testPoints");
		
		Optional<SurveyReply> currOpt = Optional.of(testSurveyReply);
		when(this.surveyReplyRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyController.getSurveyReplyById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(6, ((SurveyReply)responseEntity.getBody()).getSurveyId());
		Assert.assertEquals(120, ((SurveyReply)responseEntity.getBody()).getUserId());
		Assert.assertEquals(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)), ((SurveyReply)responseEntity.getBody()).getStarttime());
		Assert.assertEquals(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)), ((SurveyReply)responseEntity.getBody()).getEndtime());
		Assert.assertEquals("testAnswer", ((SurveyReply)responseEntity.getBody()).getAnswers());
		Assert.assertEquals("testPDF", ((SurveyReply)responseEntity.getBody()).getPdffilename());
		Assert.assertEquals("testPoints", ((SurveyReply)responseEntity.getBody()).getPoints());
		log.info(" ### testGetSurveyReplyById() - END ### ");
	}
	
	@Test
	public void testInsertSurveyReplySuccessfully() {
		log.info(" ### testInsertSurveyReplySuccessfully() - START ### ");
		SurveyReply testSurveyReply = new SurveyReply();
		testSurveyReply.setId(100L);
		testSurveyReply.setSurveyId(6L);
		testSurveyReply.setUserId(120L);
		testSurveyReply.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setAnswers("testAnswer");
		testSurveyReply.setPdffilename("testPDF");
		testSurveyReply.setPoints("testPoints");
		
		when(this.surveyRepository.findById(testSurveyReply.getSurveyId())).thenReturn(Optional.of(new Survey()));
		when(this.userRepository.findById(testSurveyReply.getUserId())).thenReturn(Optional.of(new User()));
		when(this.surveyReplyRepository.save(testSurveyReply)).thenReturn(testSurveyReply);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyController.createSurveyReply(testSurveyReply);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals(6, ((SurveyReply)responseEntity.getBody()).getSurveyId());
		Assert.assertEquals(120, ((SurveyReply)responseEntity.getBody()).getUserId());
		Assert.assertEquals(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)), ((SurveyReply)responseEntity.getBody()).getStarttime());
		Assert.assertEquals(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)), ((SurveyReply)responseEntity.getBody()).getEndtime());
		Assert.assertEquals("testAnswer", ((SurveyReply)responseEntity.getBody()).getAnswers());
		Assert.assertEquals("testPDF", ((SurveyReply)responseEntity.getBody()).getPdffilename());
		Assert.assertEquals("testPoints", ((SurveyReply)responseEntity.getBody()).getPoints());		
		log.info(" ### testInsertSurveyReplySuccessfully() - END ### ");
	}
	
	@Test
	public void testInsertSurveyReplyKOforSurveyID() {
		log.info(" ### testInsertSurveyReplyKOforSurveyID() - START ### ");
		SurveyReply testSurveyReply = new SurveyReply();
		testSurveyReply.setId(100L);
		testSurveyReply.setSurveyId(6L);
		testSurveyReply.setUserId(120L);
		testSurveyReply.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setAnswers("testAnswer");
		testSurveyReply.setPdffilename("testPDF");
		testSurveyReply.setPoints("testPoints");
		
		when(this.surveyRepository.findById(testSurveyReply.getSurveyId())).thenReturn(null);
		when(this.userRepository.findById(testSurveyReply.getUserId())).thenReturn(Optional.of(new User()));
		when(this.surveyReplyRepository.save(testSurveyReply)).thenReturn(testSurveyReply);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyController.createSurveyReply(testSurveyReply);
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		log.info(" ### testInsertSurveyReplyKOforSurveyID() - END ### ");
	}
	
	@Test
	public void testInsertSurveyReplyKOforUserID() {
		log.info(" ### testInsertSurveyReplyKOforUserID() - START ### ");
		SurveyReply testSurveyReply = new SurveyReply();
		testSurveyReply.setId(100L);
		testSurveyReply.setSurveyId(6L);
		testSurveyReply.setUserId(120L);
		testSurveyReply.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setAnswers("testAnswer");
		testSurveyReply.setPdffilename("testPDF");
		testSurveyReply.setPoints("testPoints");
		
		when(this.surveyRepository.findById(testSurveyReply.getSurveyId())).thenReturn(Optional.of(new Survey()));
		when(this.userRepository.findById(testSurveyReply.getUserId())).thenReturn(null);
		when(this.surveyReplyRepository.save(testSurveyReply)).thenReturn(testSurveyReply);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyController.createSurveyReply(testSurveyReply);
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		log.info(" ### testInsertSurveyReplyKOforUserID() - END ### ");
	}
	
	@Test
	public void testDeleteSurveyReplySuccessfully() {
		log.info(" ### testDeleteSurveyReplySuccessfully() - START ### ");
		SurveyReply testSurveyReply = new SurveyReply();
		testSurveyReply.setId(100L);
		testSurveyReply.setSurveyId(6L);
		testSurveyReply.setUserId(120L);
		testSurveyReply.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setAnswers("testAnswer");
		testSurveyReply.setPdffilename("testPDF");
		testSurveyReply.setPoints("testPoints");
		
		Optional<SurveyReply> currOpt = Optional.of(testSurveyReply);
		when(this.surveyReplyRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyController.deleteSurveyReply(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		log.info(" ### testDeleteSurveyReplySuccessfully() - END ### ");
	}
	
	@Test
	public void testUpdateSurveyReplySuccessfully() {
		log.info(" ### testUpdateSurveyReplySuccessfully() - START ### ");
		SurveyReply testSurveyReply = new SurveyReply();
		testSurveyReply.setId(100L);
		testSurveyReply.setSurveyId(6L);
		testSurveyReply.setUserId(120L);
		testSurveyReply.setStarttime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setEndtime(LocalDateTime.of(LocalDate.of(2018, 12, 3), LocalTime.of(13, 01)));
		testSurveyReply.setAnswers("testAnswer");
		testSurveyReply.setPdffilename("testPDF");
		testSurveyReply.setPoints("testPoints");
		
		Optional<SurveyReply> currOpt = Optional.of(testSurveyReply);
		when(this.surveyReplyRepository.findById(100L)).thenReturn(currOpt);
		testSurveyReply.setAnswers("testerUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.surveyReplyController.updateSurveyReply(100L, testSurveyReply);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("testerUPDATED", ((SurveyReply)responseEntity.getBody()).getAnswers());
		log.info(" ### testUpdateSurveyReplySuccessfully() - END ### ");
	}
	
	@Test
	public void getSurveyFilledTodayTest() {
		log.info("getSurveyFilledToday()Test() started");
		LocalDate date = LocalDate.now();
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		when(this.surveyReplyRepository.getSurveyReplyCountToday(start, end)).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.surveyReplyController. getSurveyFilledToday();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		log.info("getSurveyFilledToday()Test() end");
	}

	@Test
	public void getSurveyFilledLastSevenDaysTest() {
		log.info("getSurveyFilledLastSevenDaysTest() started");
		LocalDate date = LocalDate.now();
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		when(this.surveyReplyRepository.getSurveyReplyCountToday(start, end)).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.surveyReplyController.getSurveyFilledLastSevenDays();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		log.info("getSurveyFilledLastSevenDaysTest() end");
	}
	@Test
	public void getSurveyFilledYesterdayTest() {
		log.info("getSurveyFilledToday()Test() started");
		LocalDate date = LocalDate.now();
		date = date.minusDays(1);
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		when(this.surveyReplyRepository.getSurveyReplyCountToday(start, end)).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.surveyReplyController. getSurveyFilledYesterday();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		log.info("getSurveyFilledToday()Test() end");
	}

	@Test
	public void getSurveyFilledLastWeekTest() {
		log.info("getSurveyFilledLastSevenDaysTest() started");
		LocalDate date = LocalDate.now();
		date = date.minusDays(7);
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		when(this.surveyReplyRepository.getSurveyReplyCountToday(start, end)).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.surveyReplyController.getSurveyFilledLastWeek();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		log.info("getSurveyFilledLastSevenDaysTest() end");
	}
	
	@After
	public void teardown() {
		surveyReplyController = null;
	}

}