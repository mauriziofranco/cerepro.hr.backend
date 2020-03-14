package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

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
import centauri.academy.cerepro.persistence.entity.Interview;
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.SurveyInterview;
import centauri.academy.cerepro.persistence.repository.InterviewRepository;
import centauri.academy.cerepro.persistence.repository.SurveyInterviewRepository;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyInterviewControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(SurveyInterviewControllerTest.class);

	@Spy
	private SurveyInterviewController siController;
	@Mock
	private SurveyInterviewRepository siRepository;
	@Mock
	private SurveyRepository sRepository;
	@Mock
	private InterviewRepository iRepository;
	
	@Before
	public void setup() {
		siController = new SurveyInterviewController();
		ReflectionTestUtils.setField(siController, "siRepository", siRepository);
		ReflectionTestUtils.setField(siController, "sRepository", sRepository);
		ReflectionTestUtils.setField(siController, "iRepository", iRepository);
	}
	
	
	@Test
	public void testListAllSurveyInterview() {
		List<SurveyInterview> surveyInterviewList = new ArrayList<SurveyInterview>();
		surveyInterviewList.add(new SurveyInterview());
		when(this.siRepository.findAll()).thenReturn(surveyInterviewList);
		ResponseEntity<List<SurveyInterview>> responseEntity = this.siController.listAllSurveyInterview();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}
	
	@Test
	public void testInsertSurveyInterviewSuccessfully() {
		SurveyInterview testSI = new SurveyInterview ();
		testSI.setId(100l);
		testSI.setInterviewId(40l);
		testSI.setSurveyId(4l);
		testSI.setPosition(5);
		Survey testSurvey = new Survey(4L, "ciao", 20L, "ciao ciao ciao");
		Interview testInterview = new Interview ();
		testInterview.setId(40l);
		testInterview.setQuestionText("test");
		testInterview.setAnsa("ansa test");

		Optional<Survey> survey = Optional.of(testSurvey);
		Optional<Interview> interview = Optional.of(testInterview);
	
		when(siRepository.findBySurveyIdAndInterviewId(4L, 40L)).thenReturn(null);
		when(sRepository.findById(4L)).thenReturn(survey);
		when(iRepository.findById(40L)).thenReturn(interview);
		when(this.siRepository.save(testSI)).thenReturn(testSI);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.siController.createSI(testSI);
		
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals(4, ((SurveyInterview)responseEntity.getBody()).getSurveyId().longValue());
		Assert.assertEquals(40, ((SurveyInterview)responseEntity.getBody()).getInterviewId().longValue());
		Assert.assertEquals(5, ((SurveyInterview)responseEntity.getBody()).getPosition());
	}
	
	@Test
	public void testGetSurveyInterviewById() {
		SurveyInterview testSI = new SurveyInterview ();
		testSI.setId(100l);
		testSI.setInterviewId(40l);
		testSI.setSurveyId(4l);
		testSI.setPosition(5);
		Optional<SurveyInterview> currOpt = Optional.of(testSI) ;
		when(this.siRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.siController.getSurveyInterviewById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(4, ((SurveyInterview)responseEntity.getBody()).getSurveyId().longValue());
		Assert.assertEquals(40, ((SurveyInterview)responseEntity.getBody()).getInterviewId().longValue());
		Assert.assertEquals(5, ((SurveyInterview)responseEntity.getBody()).getPosition());
	}
	
	@Test
	public void testUpdateSurveyInterviewSuccessfully() {
		SurveyInterview testSI = new SurveyInterview ();
		testSI.setId(100l);
		testSI.setInterviewId(40l);
		testSI.setSurveyId(4l);
		testSI.setPosition(5);
		Optional<SurveyInterview> currOpt = Optional.of(testSI) ;
		when(this.siRepository.findById(100L)).thenReturn(currOpt);
		testSI.setPosition(20);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.siController.updateSI(100L, testSI);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(20L, ((SurveyInterview)responseEntity.getBody()).getPosition());
	}
	
	@Test
	public void testDeleteSurveyInterviewSuccessfully() {
		SurveyInterview testSI = new SurveyInterview ();
		testSI.setId(100l);
		testSI.setInterviewId(40l);
		testSI.setSurveyId(4l);
		testSI.setPosition(5);
		Optional<SurveyInterview> currOpt = Optional.of(testSI) ;
		when(this.siRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.siController.deleteSurveyInterview(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	
	@After
	public void teardown() {
		siController = null;
	}

}
