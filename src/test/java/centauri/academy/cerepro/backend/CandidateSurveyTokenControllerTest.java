package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import centauri.academy.cerepro.persistence.entity.UserTokenSurvey;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CandidateSurveyTokenControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateSurveyTokenControllerTest.class);

	@Spy
	private UserSurveyTokenController userSurveyTokenController;
	
	@Mock
	private UserSurveyTokenRepository userSurveyTokenRepository;
	
	@Before
	public void setup() {
	userSurveyTokenController = new UserSurveyTokenController();
	ReflectionTestUtils.setField(userSurveyTokenController,
	"userSurveyTokenRepository", userSurveyTokenRepository);
	}
	
	@Test
	public void testListAllUsers() {
		
	List<UserTokenSurvey> usersurveytokenList = new ArrayList<UserTokenSurvey>();
	usersurveytokenList.add(new UserTokenSurvey());
	when(this.userSurveyTokenRepository.findAll()).thenReturn(usersurveytokenList);
	ResponseEntity<List<UserTokenSurvey>> responseEntity = this.userSurveyTokenController.listAllQuestions();
	
	Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	Assert.assertEquals(1, responseEntity.getBody().size());

	}
	
	@Test
	public void testGetUserSurveyTokenById() {
		UserTokenSurvey testUserSurveyToken = new UserTokenSurvey (10L,1L,1L,"tester", null, false ) ;
		Optional<UserTokenSurvey> currOpt = Optional.of(testUserSurveyToken) ;
		when(this.userSurveyTokenRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userSurveyTokenController.getUserTokenSurveyById(10L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, ((UserTokenSurvey)responseEntity.getBody()).getUserid().longValue());
		Assert.assertEquals(1, ((UserTokenSurvey)responseEntity.getBody()).getSurveyid().longValue());
		Assert.assertEquals("tester", ((UserTokenSurvey)responseEntity.getBody()).getGeneratedtoken());
		Assert.assertEquals(null, ((UserTokenSurvey)responseEntity.getBody()).getExpirationdate());
		Assert.assertEquals(false, ((UserTokenSurvey)responseEntity.getBody()).isExpired());

	}
	
	@Test
	public void testInsertUserSurveyTokenSuccessfully() {
		UserTokenSurvey testUserSurveyToken = new UserTokenSurvey (10L,1L,1L,"tester", LocalDateTime.now().plusHours(10), false ) ;
		when(this.userSurveyTokenRepository.save(testUserSurveyToken)).thenReturn(testUserSurveyToken);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userSurveyTokenController.createUserTokenSurvey(testUserSurveyToken);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals(10, ((UserTokenSurvey)responseEntity.getBody()).getId().longValue());
		Assert.assertEquals(1, ((UserTokenSurvey)responseEntity.getBody()).getSurveyid().longValue());
		Assert.assertEquals(1, ((UserTokenSurvey)responseEntity.getBody()).getUserid().longValue());
	}
	
	@Test
	public void testDeleteUserSurveyTokenSuccessfully() {
		UserTokenSurvey testUserSurveyToken = new UserTokenSurvey (10L,1L,1L,"tester", null, false ) ;
		Optional<UserTokenSurvey> currOpt = Optional.of(testUserSurveyToken) ;
		when(this.userSurveyTokenRepository.findById(10L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userSurveyTokenController.deleteUserSurveyToken(10L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
//	@Test
//	public void testUpdateUserSurveyTokenSuccessfully() {
//		UserTokenSurvey testUserSurveyToken = new UserTokenSurvey (10L,1L,1L,"tester", null ) ;
//		Optional<UserTokenSurvey> currOpt = Optional.of(testUserSurveyToken) ;
//		when(this.usersurveytokenRepository.findById(100L)).thenReturn(currOpt);
//		testUserSurveyToken.setDescription("testerUPDATED");
//		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userSurveyTokenController.updateUserTokenSurvey(10L, testUserSurveyToken);
//		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		Assert.assertEquals("testerUPDATED", ((UserTokenSurvey)responseEntity.getBody()).getDescription());
//	}
		
	@After
	public void teardown() {
	userSurveyTokenController = null;
	}
	
	// create update delete
	
	
}
