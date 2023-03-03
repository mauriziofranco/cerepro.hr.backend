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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.CandidateSurveyToken;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.candidatesurveytoken.CandidateSurveyTokenRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.service.UserService;

/**
 * Unit test for UserController
 * 
 * @author joffre
 * @author anna
 */

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes = CeReProBackendApplication.class)
public class UserControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@Spy
	private UserService userService;
	@Spy
	private UserController userController;
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private CandidateSurveyTokenRepository candidateSurveyTokenRepository;
	@Mock
	private SurveyReplyRepository surveyReplyRepository;

	/**
	 * setup method prepares an instance of UserController and injects the mock
	 * UserRepository,RoleRepository (foreign key (role) references roles(level)),
	 * CandidateRepository, EmployeeRepository, etc... (foreign key (user_id)
	 * references users(id)) using Spring’s ReflectionTestUtils utility class by
	 * calling its setField method.
	 */
	@Before
	public void setup() {
		userController = new UserController();
		ReflectionTestUtils.setField(userController, "userRepository", userRepository);
		ReflectionTestUtils.setField(userController, "roleRepository", roleRepository);
		ReflectionTestUtils.setField(userController, "userService", userService);
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
	}

	/**
	 * testListAllUsers() method tests if the method getUsers() is really able to
	 * select all tuples from the users' table
	 */
	@Test
	public void testListAllUsers() {

		logger.info("testListAllUsers()  ---------------------- START");
		List<User> userList = new ArrayList<User>();
		userList.add(new User());

		when(this.userRepository.findAll()).thenReturn(userList);
		ResponseEntity<List<User>> responseEntity = this.userController.getUsers();

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
		logger.info("testListAllUsers()  ---------------------- END");
	}

	/**
	 * testGetUserById() method tests if the method getUserById() is really able to
	 * select a tuple from the users' table based on the Id passed as parameter
	 */
	@Test
	public void testGetUserById() {

		logger.info("testGetUserById()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);

		Optional<User> currOpt = Optional.of(user);
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.getUserById(100L);

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("pippo@prova.com", ((User) responseEntity.getBody()).getEmail());
		Assert.assertEquals("pippo", ((User) responseEntity.getBody()).getPassword());
		Assert.assertEquals("pippo", ((User) responseEntity.getBody()).getFirstname());
		logger.info("testGetUserById()  ---------------------- END");
	}

	/**
	 * testInsertUserSuccesfully() method tests if the method createUser() is really
	 * able to create a new tuple in the users' table
	 */
	@Test
	public void testInsertUserSuccesfully() {

		logger.info("testInsertUserSuccesfully()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRole(10);

		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
		Optional<User> optUser = Optional.ofNullable(null);
		when(this.userRepository.findByEmail(user.getEmail())).thenReturn(optUser);
		when(this.userRepository.save(user)).thenReturn(user);
		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);

		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals("pippo@prova.com", ((User) responseEntity.getBody()).getEmail());
//		Assert.assertEquals("$2a$10$/dkQriasnwbmhxzdjDw.YO9WdTBwJHoPDhhWrceWTcZXfsVwtQEhC", ((User) responseEntity.getBody()).getPassword());
		Assert.assertEquals("pippo", ((User) responseEntity.getBody()).getFirstname());
		Assert.assertEquals("prova", ((User) responseEntity.getBody()).getLastname());
		Assert.assertEquals(10, ((User) responseEntity.getBody()).getRole());
		logger.info("testInsertUserSuccesfully()  ---------------------- END");
	}

	/**
	 * testInsertUserKOForEmail() method tests if the method createUser() is really
	 * able for CONSTRAINT uniqueEmail UNIQUE (email)
	 * 
	 */
	@Test
	public void testInsertUserKOForEmail() {

		logger.info("testInsertUserKOForEmail()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);

		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
		Optional<User> optUser = Optional.ofNullable(user);
		when(this.userRepository.findByEmail(user.getEmail())).thenReturn(optUser);
		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);

		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testInsertUserKOForEmail()  ---------------------- END");
	}

	/**
	 * testInsertUserKOForRole() method tests if the method createUser() is really
	 * able for foreign key (role) references roles(level)
	 * 
	 */
	@Test
	public void testInsertUserKOForRole() {

		logger.info("testInsertUserKOForRole()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);

		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(null);
		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);

		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testInsertUserKOForRole()  ---------------------- END");
	}

	/**
	 * testUpdateUserSuccessfully() method tests if the method updateUser() is
	 * really able to update fields in the users' table
	 */
	@Test
	public void testUpdateUserSuccessfully() {

		logger.info("testUpdateUserSuccessfully()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);

		Optional<User> currOpt = Optional.of(user);
		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		user.setPassword("pippoUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.updateUser(100L, user);

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("pippoUPDATED", ((User) responseEntity.getBody()).getPassword());
		logger.info("testUpdateUserSuccessfully()  ---------------------- END");
	}

	/**
	 * testUpdateUserKOForRole() method tests if the method udateUser() is really
	 * able for foreign key (role) references roles(level)
	 * 
	 */
	@Test
	public void testUpdateUserKOForRole() {

		logger.info("testUpdateUserKOForRole()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);

		Optional<User> currOpt = Optional.of(user);
		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(null);
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		user.setPassword("pippoUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.updateUser(100L, user);

		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testUpdateUserKOForRole()  ---------------------- END");
	}

	/**
	 * testDeleteUserSuccessfully() method tests if the method deleteUser() is
	 * really able to delete a tuple in the users' table based on the Id passed as
	 * parameter
	 */
	@Test
	public void testDeleteUserSuccessfully() {

		logger.info("testDeleteUserSuccessfully()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);

		Optional<User> currOpt = Optional.of(user);

		List<CandidateSurveyToken> userSurveyTokenList = new ArrayList<CandidateSurveyToken>();
		List<SurveyReply> surveyReplyList = new ArrayList<SurveyReply>();

		when(this.userRepository.findById(100L)).thenReturn(currOpt);
//		when(this.candidateRepository.findByUserId(100L)).thenReturn(candidateList);
		when(this.candidateSurveyTokenRepository.findByCandidateId(100L)).thenReturn(userSurveyTokenList);
		when(this.surveyReplyRepository.findByCandidateId(100L)).thenReturn(surveyReplyList);

		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.deleteUser(100L);
		logger.info("testDeleteUserSuccessfully()  ---------------------- END");
	}

	

	/**
	 * teardown() method sets userController to null
	 */
	@After
	public void teardown() {
		userController = null;
	}

}