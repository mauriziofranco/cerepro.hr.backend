package centauri.academy.cerepro.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.service.CandidateService;

/**
 * Unit test for CandidateController
 * @author giacomo
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CandidateControllerTest extends AbstractMockModelGenerator {
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateControllerTest.class);
	
	 @Spy
	 private CandidateController candidateController;
     @Mock
     private CandidateService candidateService;
     
     private final long FAKE_CANDIDATE_ID = 1l ;
     private final String FAKE_CANDIDATE_CV_EXTERNAL_PATH = "test_path" ;
     
     /**
      * setup method prepares an instance of CandidateController and injects the mock CandidateRepository
      * using Spring’s ReflectionTestUtils utility class by calling its setField method.
      */
     @Before
     public void setup() {
    	 candidateController = new CandidateController();
    	 ReflectionTestUtils.setField(candidateController,"candidateService", candidateService);
     }
     
     /**
      * testListAllCandidate() method tests if the method listAllCandidate()
      * is really able to select all tuples from the candidates' table
      */
     @Test
     public void testListAllCandidate() {
    	 logger.info("##### Test-testListAllCandidate() ---- Start #####");
	     List<Candidate> candidateList = new ArrayList<Candidate>();
	     candidateList.add(getFakeMockCandidate());
	     when(this.candidateService.getAll()).thenReturn(candidateList);
	     ResponseEntity<List<Candidate>> responseEntity = this.candidateController.listAllCandidate();
	     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	     assertEquals(1, responseEntity.getBody().size());
	     candidateList.remove(0);
	     when(this.candidateService.getAll()).thenReturn(candidateList);
	     ResponseEntity<List<Candidate>> responseEntityEmpty = this.candidateController.listAllCandidate();
	     assertEquals(HttpStatus.NO_CONTENT, responseEntityEmpty.getStatusCode());
	     assertEquals(0, responseEntity.getBody().size());
	     logger.info("##### Test-testListAllCandidate() ---- End #####");
     }
     
     /**
      * testGetCandidateById() method tests if the method getCandidateById()
      * is really able to select a tuple from the candidates' table based on the Id passed as parameter
      */
     @Test
     public void testGetCandidateById() {
    	 logger.info("##### Test-testGetCandidateById() ---- Start #####");
	     Candidate c = getFakeMockCandidate();
	     
	     c.setId(FAKE_CANDIDATE_ID);
	     Optional<Candidate> currCandidate = Optional.of(c);
	     when(this.candidateService.getById(1l)).thenReturn(currCandidate);
	     ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateController.getCandidateById(1l);
	     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	     assertEquals(FAKE_CANDIDATE_ID,((Candidate)responseEntity.getBody()).getId().longValue());
	     ResponseEntity<CeReProAbstractEntity> responseEntityNotFound = this.candidateController.getCandidateById(2l);
	     assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
	     logger.info("##### Test-testGetCandidateById() ---- End #####");
     }
     
     /**
      * testCreateCandidate() method tests if the method createCandidate()
      * is really able to create a new tuple in the candidates' table
      */
     @Test
     public void testCreateCandidate() {
    	 logger.info("##### Test-testCreateCandidate() ---- Start #####");
	     Candidate c = getFakeMockCandidate();
	     when(this.candidateService.insert(c)).thenReturn(c);
	     ResponseEntity<Candidate> responseEntity = this.candidateController.createCandidate(c);
	     assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	     assertEquals(c,((Candidate)responseEntity.getBody()));
	     logger.info("##### Test-testCreateCandidate() ---- End #####");
     }
     
     /**
      * testUpdateCandidate() method tests if the method updateCandidate()
      * is really able to update fields in the candidates' table
      */
     @Test
     public void testUpdateCandidate() {
    	 logger.info("##### Test-testUpdateCandidate() ---- Start #####");
	     Candidate c = getFakeMockCandidate();
	     c.setId(FAKE_CANDIDATE_ID);
	     Optional<Candidate> currCandidate = Optional.of(c);
	     when(this.candidateService.getById(FAKE_CANDIDATE_ID)).thenReturn(currCandidate);
	     c.setCvExternalPath(FAKE_CANDIDATE_CV_EXTERNAL_PATH);
	     ResponseEntity<CeReProAbstractEntity> responseEntityUpdated = this.candidateController.updateCandidate(1l, c);
	     assertEquals(HttpStatus.OK, responseEntityUpdated.getStatusCode());
	     assertEquals(FAKE_CANDIDATE_CV_EXTERNAL_PATH,((Candidate)responseEntityUpdated.getBody()).getCvExternalPath());
	     ResponseEntity<CeReProAbstractEntity> responseEntityUpdatedNotFound = this.candidateController.updateCandidate(10l, c);
	     assertEquals(HttpStatus.NOT_FOUND, responseEntityUpdatedNotFound.getStatusCode());
	     logger.info("##### Test-testUpdateCandidate() ---- End #####");
     }
     
     /**
      * testDeleteCandidate() method tests if the method deleteCandidate()
      * is really able to delete a tuple in the candidates' table based on the Id passed as parameter
      */
     @Test
     public void testDeleteCandidate() {
    	 logger.info("##### Test-testDeleteCandidate() ---- Start #####");
	     Candidate c = getFakeMockCandidate();
	     c.setId(1l);
	     Optional<Candidate> currCandidate = Optional.of(c);
	     when(this.candidateService.getById(1l)).thenReturn(currCandidate);
	     ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateController.deleteCandidate(1l);
	     assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	     ResponseEntity<CeReProAbstractEntity> responseEntityNotFound = this.candidateController.deleteCandidate(10l);
	     assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
	     logger.info("##### Test-testDeleteCandidate() ---- End ######");
     }
     
     /**
      * teardown() method sets candidateController to null
      */
     @After
     public void teardown() {
    	 candidateController = null;
     }
     
     @Test
 	public void getTodayRegistratedCandidates() {
 		logger.info("getTodayRegistratedCandidates - START");
 		LocalDate date = LocalDate.now();
 		long l = 1l;
 		when(this.candidateService.getRegisteredCandidatesInDate(date)).thenReturn(l);
 		ResponseEntity<Long> responseEntity = candidateController.getTodayRegistratedCandidates();
 		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 		assertTrue(((Long) responseEntity.getBody()).equals(l));
 		logger.info("getTodayRegistratedCandidates - END");
 	}

 	@Test
 	public void getYesterdayRegistratedCandidates() {
 		logger.info("getYesterdayRegistratedCandidates - START");
 		LocalDate date = LocalDate.now();
 		date = date.minusDays(1);
 		long l = 1l;
 		when(this.candidateService.getRegisteredCandidatesInDate(date)).thenReturn(l);
 		ResponseEntity<Long> responseEntity = candidateController.getYesterdayRegistratedCandidates();
 		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 		assertTrue(((Long) responseEntity.getBody()).equals(l));
 		logger.info("getYesterdayRegistratedCandidates - END");
 	}

 	@Test
 	public void getLastSevenDaysRegistratedCandidates() {
 		logger.info("getLastSevenDaysRegistratedCandidates() - START");
 		long l = 1l;
        when(this.candidateService.getRegisteredCandidatesFromDaysAgo(7)).thenReturn(l);
 		ResponseEntity<Long> responseEntity = candidateController.getLastSevenDaysRegistratedCandidates();
 		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 		assertTrue(((Long) responseEntity.getBody()).equals(l));
 		logger.info("getLastSevenDaysRegistratedCandidates() - END");
 	}

 	@Test
 	public void getRegistratedCandidatesOnLastTwoWeeks() {
 		logger.info("getRegistratedCandidatesOnLastTwoWeeks() - START");
 		long l = 1l;
         when(this.candidateService.getRegisteredCandidatesFromDaysAgo(14)).thenReturn(l);
 		ResponseEntity<Long> responseEntity = candidateController.getRegistratedCandidatesOnLastTwoWeeks();
 		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 		assertTrue(((Long) responseEntity.getBody()).equals(l));
 		logger.info("getRegistratedCandidatesOnLastTwoWeeks() - END");
 	}
}
