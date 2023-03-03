package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import centauri.academy.cerepro.persistence.entity.CandidateSurveyToken;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.repository.candidatesurveytoken.CandidateSurveyTokenRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CandidateSurveyTokenControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(CandidateSurveyTokenControllerTest.class);

	@Spy
	private CandidateSurveyTokenController candidateSurveyTokenController;

	@Mock
	private CandidateSurveyTokenRepository candidateSurveyTokenRepository;

	@Before
	public void setup() {
		candidateSurveyTokenController = new CandidateSurveyTokenController();
		ReflectionTestUtils.setField(candidateSurveyTokenController, "candidateSurveyTokenRepository",
				candidateSurveyTokenRepository);
	}

	@Test
	public void testListAllUsers() {

		List<CandidateSurveyToken> usersurveytokenList = new ArrayList<CandidateSurveyToken>();
		usersurveytokenList.add(new CandidateSurveyToken());
		when(this.candidateSurveyTokenRepository.findAll()).thenReturn(usersurveytokenList);
		ResponseEntity<List<CandidateSurveyToken>> responseEntity = this.candidateSurveyTokenController.listAllQuestions();

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());

	}

	@Test
	public void testGetUserSurveyTokenById() {
		CandidateSurveyToken testUserSurveyToken = new CandidateSurveyToken(10L, 1L, 1L, "tester", null, false);
		Optional<CandidateSurveyToken> currOpt = Optional.of(testUserSurveyToken);
		when(this.candidateSurveyTokenRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateSurveyTokenController
				.getCandidateSurveyTokenById(10L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, ((CandidateSurveyToken) responseEntity.getBody()).getCandidateId().longValue());
		Assert.assertEquals(1, ((CandidateSurveyToken) responseEntity.getBody()).getSurveyId().longValue());
		Assert.assertEquals("tester", ((CandidateSurveyToken) responseEntity.getBody()).getGeneratedToken());
		Assert.assertEquals(null, ((CandidateSurveyToken) responseEntity.getBody()).getExpirationDateTime());
		Assert.assertEquals(false, ((CandidateSurveyToken) responseEntity.getBody()).isExpired());

	}

	@Test
	public void testInsertUserSurveyTokenSuccessfully() {
		CandidateSurveyToken testUserSurveyToken = new CandidateSurveyToken(10L, 1L, 1L, "tester",
				LocalDateTime.now().plusHours(10), false);
		when(this.candidateSurveyTokenRepository.save(testUserSurveyToken)).thenReturn(testUserSurveyToken);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateSurveyTokenController
				.createCandidateSurveyToken(testUserSurveyToken);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals(10, ((CandidateSurveyToken) responseEntity.getBody()).getId().longValue());
		Assert.assertEquals(1, ((CandidateSurveyToken) responseEntity.getBody()).getSurveyId().longValue());
		Assert.assertEquals(1, ((CandidateSurveyToken) responseEntity.getBody()).getCandidateId().longValue());
	}

	@Test
	public void testDeleteUserSurveyTokenSuccessfully() {
		CandidateSurveyToken testUserSurveyToken = new CandidateSurveyToken(10L, 1L, 1L, "tester", null, false);
		Optional<CandidateSurveyToken> currOpt = Optional.of(testUserSurveyToken);
		when(this.candidateSurveyTokenRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateSurveyTokenController
				.deleteUserSurveyToken(10L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

//	@Test
//	public void testUpdateUserSurveyTokenSuccessfully() {
//		CandidateSurveyToken testUserSurveyToken = new CandidateSurveyToken (10L,1L,1L,"tester", null ) ;
//		Optional<CandidateSurveyToken> currOpt = Optional.of(testUserSurveyToken) ;
//		when(this.usersurveytokenRepository.findById(100L)).thenReturn(currOpt);
//		testUserSurveyToken.setDescription("testerUPDATED");
//		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateSurveyTokenController.updateUserTokenSurvey(10L, testUserSurveyToken);
//		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		Assert.assertEquals("testerUPDATED", ((CandidateSurveyToken)responseEntity.getBody()).getDescription());
//	}

	@After
	public void teardown() {
		candidateSurveyTokenController = null;
	}

	// create update delete

}
