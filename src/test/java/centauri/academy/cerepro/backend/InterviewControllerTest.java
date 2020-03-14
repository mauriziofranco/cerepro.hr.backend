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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Interview;

import centauri.academy.cerepro.persistence.repository.InterviewRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class InterviewControllerTest {
	
	@Spy
	private InterviewController interviewController;
	@Mock
	private InterviewRepository interviewRepository;
	
	@Before
	public void setup() {
		interviewController = new InterviewController();
		ReflectionTestUtils.setField(interviewController, "interviewRepository", interviewRepository);
	}
	
	@Test
	public void testListAllInterview() {
		List<Interview> interviewList = new ArrayList<Interview>();
		interviewList.add(new Interview());
		when(this.interviewRepository.findAll()).thenReturn(interviewList);
		ResponseEntity<List<Interview>> responseEntity = this.interviewController.listAllInterview();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}
	
	@Test
	public void testGetInterviewById() {
		Interview testInterview = new Interview();
		testInterview.setId(100l);
		testInterview.setAnsa("prova");
		testInterview.setAnsb("prova");
		Optional<Interview> currOpt = Optional.of(testInterview) ;
		when(this.interviewRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.interviewController.getInterviewById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("prova", ((Interview)responseEntity.getBody()).getAnsa());
		Assert.assertEquals("prova", ((Interview)responseEntity.getBody()).getAnsb());
		
	}
	
	@Test
	public void testInsertInterviewSuccessfully() {
		Interview testInterview = new Interview();
		testInterview.setId(100l);
		testInterview.setAnsa("prova");
		testInterview.setAnsb("prova");
		when(this.interviewRepository.save(testInterview)).thenReturn(testInterview);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.interviewController.createInterview(testInterview);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals("prova", ((Interview)responseEntity.getBody()).getAnsa());
		Assert.assertEquals("prova", ((Interview)responseEntity.getBody()).getAnsb());
	}
	
	@Test
	public void testDeleteInterviewSuccessfully() {
		Interview testInterview = new Interview();
		testInterview.setId(100l);
		testInterview.setAnsa("prova");
		testInterview.setAnsb("prova");
		Optional<Interview> currOpt = Optional.of(testInterview) ;
		when(this.interviewRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.interviewController.deleteInterview(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateInterviewSuccessfully() {
		Interview testInterview = new Interview();
		testInterview.setId(100l);
		testInterview.setAnsa("prova");
		testInterview.setAnsb("prova");
		Optional<Interview> currOpt = Optional.of(testInterview) ;
		when(this.interviewRepository.findById(100L)).thenReturn(currOpt);
		testInterview.setAnsa("testerUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.interviewController.updateInterview(100L, testInterview);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("testerUPDATED", ((Interview)responseEntity.getBody()).getAnsa());
	}
	
	@After
	public void teardown() {
		interviewController = null;
	}

}
