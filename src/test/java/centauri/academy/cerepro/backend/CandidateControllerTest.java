package centauri.academy.cerepro.backend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
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
public class CandidateControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateControllerTest.class);
	
	 @Spy
	 private CandidateController candidateController;
     @Mock
     private CandidateService candidateService;
     
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
	     candidateList.add(new Candidate(1l, "test",2l));
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
	     Candidate c = new Candidate(1l, "test",2l);
	     c.setId(1l);
	     Optional<Candidate> currCandidate = Optional.of(c);
	     when(this.candidateService.getById(1l)).thenReturn(currCandidate);
	     ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateController.getCandidateById(1l);
	     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	     assertEquals(1,((Candidate)responseEntity.getBody()).getId().longValue());
	     assertEquals(1,((Candidate)responseEntity.getBody()).getUserId().longValue());
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
	     Candidate c = new Candidate(1l, "test",2l);
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
	     Candidate c = new Candidate(1l, "test",2l);
	     c.setId(1l);
	     Optional<Candidate> currCandidate = Optional.of(c);
	     when(this.candidateService.getById(1l)).thenReturn(currCandidate);
	     c.setCvExternalPath("test");
	     ResponseEntity<CeReProAbstractEntity> responseEntityUpdated = this.candidateController.updateCandidate(1l, c);
	     assertEquals(HttpStatus.OK, responseEntityUpdated.getStatusCode());
	     assertEquals("test",((Candidate)responseEntityUpdated.getBody()).getCvExternalPath());
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
	     Candidate c = new Candidate(1l, "test",2l);
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
}
