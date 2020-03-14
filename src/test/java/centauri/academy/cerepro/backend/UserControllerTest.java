package centauri.academy.cerepro.backend;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Employee;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.UserTokenSurvey;
import centauri.academy.cerepro.persistence.repository.EmployeeRepository;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;
import centauri.academy.cerepro.service.UserService;

/**
 * Unit test for UserController
 * @author joffre
 * @author anna
 */

@RunWith(SpringRunner.class)
@SpringBootTest (classes = CeReProBackendApplication.class, webEnvironment =
WebEnvironment.RANDOM_PORT)
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
	private CandidateRepository candidateRepository;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private UserSurveyTokenRepository  userSurveyTokenRepository;
	@Mock
	private SurveyReplyRepository  surveyReplyRepository;
	
	/**
     * setup method prepares an instance of UserController and injects the mock UserRepository,RoleRepository (foreign key (role) references roles(level)),
     *  CandidateRepository, EmployeeRepository, etc... (foreign key (user_id) references users(id))
     * using Spring’s ReflectionTestUtils utility class by calling its setField method.
     */
	@Before
	public void setup() {
		userController = new UserController();
		ReflectionTestUtils.setField(userController, "userRepository", userRepository);
		ReflectionTestUtils.setField(userController, "roleRepository", roleRepository);
		ReflectionTestUtils.setField(userController, "candidateRepository", candidateRepository); 
		ReflectionTestUtils.setField(userController, "employeeRepository", employeeRepository);  		
		ReflectionTestUtils.setField(userController, "userSurveyTokenRepository", userSurveyTokenRepository); 
		ReflectionTestUtils.setField(userController, "surveyReplyRepository", surveyReplyRepository); 
		ReflectionTestUtils.setField(userController, "userService", userService);
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		ReflectionTestUtils.setField(userService, "userService", userService);
	}
	
	/**
     * testListAllUsers() method tests if the method getUsers()
     * is really able to select all tuples from the users' table
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
     * testGetUserById() method tests if the method getUserById()
     * is really able to select a tuple from the users' table based on the Id passed as parameter
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");

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
     * testInsertUserSuccesfully() method tests if the method createUser()
     * is really able to create a new tuple in the users' table
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3)); 
//		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
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
		Assert.assertEquals(LocalDate.of(2018, 12, 3), ((User) responseEntity.getBody()).getDateOfBirth());
//		Assert.assertEquals(LocalDateTime.now(), ((User) responseEntity.getBody()).getRegdate() );
		Assert.assertEquals(10, ((User) responseEntity.getBody()).getRole());
		Assert.assertEquals("impPippo", ((User) responseEntity.getBody()).getImgpath());
		logger.info("testInsertUserSuccesfully()  ---------------------- END");
	}
	
	/**
     * testInsertUserKOForEmail() method tests if the method createUser() 
     * is really able for CONSTRAINT uniqueEmail UNIQUE (email)
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
		Optional<User> optUser = Optional.ofNullable(user);
		when(this.userRepository.findByEmail(user.getEmail())).thenReturn(optUser);
		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testInsertUserKOForEmail()  ---------------------- END");
	}
	
	/**
     * testInsertUserKOForRole() method tests if the method createUser() 
     * is really able for foreign key (role) references roles(level)
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(null);
		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testInsertUserKOForRole()  ---------------------- END");
	}
	
	/**
     * testUpdateUserSuccessfully() method tests if the method updateUser()
     * is really able to update fields in the users' table
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		Optional<User> currOpt = Optional.of(user) ;
		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		user.setPassword("pippoUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.updateUser(100L, user);
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("pippoUPDATED", ((User)responseEntity.getBody()).getPassword());
		logger.info("testUpdateUserSuccessfully()  ---------------------- END");
	}
	
	/**
     * testUpdateUserKOForRole() method tests if the method udateUser() 
     * is really able for foreign key (role) references roles(level)
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		Optional<User> currOpt = Optional.of(user) ;
		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(null);
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		user.setPassword("pippoUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.updateUser(100L, user);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testUpdateUserKOForRole()  ---------------------- END");
	}
	
	/**
     * testDeleteUserSuccessfully() method tests if the method deleteUser()
     * is really able to delete a tuple in the users' table based on the Id passed as parameter
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
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
	 
		Optional<User> currOpt = Optional.of(user) ;
		
		List<Candidate> candidateList = new ArrayList<Candidate>(); 
		List<Employee> employeeList = new ArrayList<Employee>();
		List<UserTokenSurvey> userSurveyTokenList = new ArrayList<UserTokenSurvey>();
		List<SurveyReply> surveyReplyList = new ArrayList<SurveyReply>();
	  
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		when(this.candidateRepository.findByUserId(100L)).thenReturn(candidateList); 
		when(this.employeeRepository.findByUserId(100L)).thenReturn(employeeList); 
		when(this.userSurveyTokenRepository.findByUserId(100L)).thenReturn(userSurveyTokenList); 
		when(this.surveyReplyRepository.findByUserId(100L)).thenReturn(surveyReplyList); 
		 
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.deleteUser(100L);  
		logger.info("testDeleteUserSuccessfully()  ---------------------- END");
	}
	
	/**
     * testDeleteUserKOForCandidateUserId() method tests if the method deleteUser() 
     * is really able for foreign key (user_id) references users(id) into candidates' table
     *  
     */
	@Test
	public void testDeleteUserKOForCandidateUserId() {
		
		logger.info("testDeleteUserKOForCandidateUserId()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		Optional<User> currOpt = Optional.of(user) ;
		
		List<Candidate> candidateList = new ArrayList<Candidate>();
		candidateList.add(new Candidate());
		
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		when(this.candidateRepository.findByUserId(100L)).thenReturn(candidateList); 
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.deleteUser(100L);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testDeleteUserKOForCandidateUserId()  ---------------------- END");
	}
	
	/**
     * testDeleteUserKOForEmployeeUserId() method tests if the method deleteUser() 
     * is really able for foreign key (user_id) references users(id) into employees' table
     *  
     */
	@Test
	public void testDeleteUserKOForEmployeeUserId() {
		
		logger.info("testDeleteUserKOForEmployeeUserId()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		Optional<User> currOpt = Optional.of(user) ;
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(new Employee());
		
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		when(this.employeeRepository.findByUserId(100L)).thenReturn(employeeList);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.deleteUser(100L);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testDeleteUserKOForEmployeeUserId()  ---------------------- END");
	}
	
	/**
     * testDeleteUserKOForUserTokenSurveyUserId() method tests if the method deleteUser() 
     * is really able for foreign key (user_id) references users(id) into usersurveytoken table
     *  
     */
	@Test
	public void testDeleteUserKOForUserTokenSurveyUserId() {
		
		logger.info("testDeleteUserKOForUserTokenSurveyUserId()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		Optional<User> currOpt = Optional.of(user) ;
		
		List<UserTokenSurvey> userSurveyTokenList = new ArrayList<UserTokenSurvey>();
		userSurveyTokenList.add(new UserTokenSurvey());
		
		when(this.userRepository.findById(100L)).thenReturn(currOpt);  
		when(this.userSurveyTokenRepository.findByUserId(100L)).thenReturn(userSurveyTokenList); 
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.deleteUser(100L);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testDeleteUserKOForUserTokenSurveyUserId()  ---------------------- END");
	}
	
	/**
     * testDeleteUserKOForSurveyReplyUserId() method tests if the method deleteUser() 
     * is really able for foreign key (user_id) references users(id) into surveyreplies table
     *  
     */
	@Test
	public void testDeleteUserKOForSurveyReplyUserId() {
		
		logger.info("testDeleteUserKOForSurveyReplyUserId()  ---------------------- START");
		User user = new User();
		user.setId(100L);
		user.setEmail("pippo@prova.com");
		user.setPassword("pippo");
		user.setFirstname("pippo");
		user.setLastname("prova");
		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
		user.setRegdate(LocalDateTime.now());
		user.setRole(10);
		user.setImgpath("impPippo");
		
		Optional<User> currOpt = Optional.of(user) ;
		
		List<SurveyReply> surveyReplyList = new ArrayList<SurveyReply>(); 
		surveyReplyList.add(new SurveyReply());
		
		when(this.userRepository.findById(100L)).thenReturn(currOpt);
		when(this.surveyReplyRepository.findByUserId(100L)).thenReturn(surveyReplyList);  
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.deleteUser(100L);
		
		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		logger.info("testDeleteUserKOForSurveyReplyUserId()  ---------------------- END");
	}
	
	
	/**
     * teardown() method sets userController to null
     */
	@After
	public void teardown() {
		userController = null;
	}
	@Test
	public void  getUserRegistratedTodayTest() {
		logger.info("getUserRegistratedToday()  ---------------------- START");
		LocalDate date = LocalDate.now();
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		
		when(this.userRepository.getUserRegdateInPeriod(start, end )).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.userController.getUserRegistratedToday();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		logger.info("getUserRegistratedToday()  ---------------------- end");
	}
	@Test
	public void  getUserRegistratedYesterdayTest() {
		logger.info("getUserRegistratedToday()  ---------------------- START");
		LocalDate date = LocalDate.now();
		date = date.minusDays(1);
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		
		when(this.userRepository.getUserRegdateInPeriod(start, end )).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.userController.getUserRegistratedYesterday();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		logger.info("getUserRegistratedToday()  ---------------------- end");
	}
	
	@Test
	public void getUserRegistratedLastSevenDaysTest() {
		logger.info("getUserRegistratedLastSevenDays()  ---------------------- START");
		LocalDate date = LocalDate.now();
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;
		
		when(this.userRepository.getUserRegdateInPeriod(start, end)).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.userController. getUserRegistratedLastSevenDays();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));	
		logger.info("getUserRegistratedLastSevenDays()  ---------------------- end");
	}
	@Test
	public void  getUserRegistratedLastWeekTest() {
		logger.info("getUserRegistratedLastWeek()  ---------------------- START");
		LocalDate date = LocalDate.now();
		date = date.minusDays(7);
		LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
		LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
		Long objL = 1L;
		long l = objL;	
		when(this.userRepository.getUserRegdateInPeriod(start, end)).thenReturn(l);
		ResponseEntity<Long> responseEntity = this.userController.getUserRegistratedLastWeek();
		Assert.assertEquals( HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(((Long)responseEntity.getBody()).equals(l));
		logger.info("getUserRegistratedLastWeek()  ---------------------- end");
	}
	
	
}