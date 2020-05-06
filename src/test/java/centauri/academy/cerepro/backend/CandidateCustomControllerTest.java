package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List; 

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
import centauri.academy.cerepro.persistence.entity.custom.CandidateCustom;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;
import centauri.academy.cerepro.service.CandidateService;

/**
 * Unit test for CandidateCustomControllerTest
 * @author joffre
 */

@RunWith(SpringRunner.class)
@SpringBootTest (classes = CeReProBackendApplication.class, webEnvironment =
WebEnvironment.RANDOM_PORT)
public class CandidateCustomControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateCustomControllerTest.class);
	
	@Spy
	private CandidateCustomController candidateCustomController;
	@Mock
	private CandidateService candidateService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	  
	/**
     * setup method prepares an instance of UserController and injects the mock CandidateRepository UserRepository,RoleRepository  
     *   
     * using Spring’s ReflectionTestUtils utility class by calling its setField method.
     */
	@Before
	public void setup() {
		candidateCustomController = new CandidateCustomController();
 		ReflectionTestUtils.setField(candidateCustomController, "candidateService", candidateService);  
		ReflectionTestUtils.setField(candidateCustomController, "userRepository", userRepository);
		ReflectionTestUtils.setField(candidateCustomController, "roleRepository", roleRepository);
 		
	}
	
//	/**
//     * testGetAllCandidateCustom() method tests if the method getAllCandidateCustom()
//     * is really able to select all tuples from the candidates' and user table
//     * 
//     * * COMMENTED ON 24/01/20 because method of controller is no more used by the frontend!!!!!!!! by maurizio
//     * 
//     */
//	@Test
//	public void testGetAllCandidateCustom() {
//		
//		logger.info("testGetAllCandidateCustom()  ---------------------- START");
//		List<CandidateCustom> candidateCustomList = new ArrayList<CandidateCustom>();
//		candidateCustomList.add(new CandidateCustom());
//		
//		when(this.candidateService.getAllCustom()).thenReturn(candidateCustomList);
//		ResponseEntity<List<CandidateCustom>> responseEntity = this.candidateCustomController.getAllCandidateCustom();
//		
//		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		Assert.assertEquals(1, responseEntity.getBody().size());
//		logger.info("testGetAllCandidateCustom()  ---------------------- END");
//	}
	
	/**
     * testGetCandidateCustomById() method tests if the method getCandidateCustomById()
     * is really able to select a tuple from the candidates' and users table based on the Id passed as parameter
     */ 
	@Test
	public void testGetCandidateCustomById() {
		
		logger.info("testGetCandidateCustomById()  ---------------------- START");
		CandidateCustom candidateCustom = new CandidateCustom();
	  
		candidateCustom.setId(100L);
		candidateCustom.setUserId(4L); 
		candidateCustom.setDomicileCity("provaprova");  
//		candidateCustom.setDomicileStreetName("provaprovaprova");
		candidateCustom.setMobile("4124214124");
		candidateCustom.setEmail("pippo@prova.com"); 
		candidateCustom.setFirstname("pippo"); 
		candidateCustom.setLastname("prova");
		candidateCustom.setDateOfBirth(LocalDate.of(2018, 12, 3)); 
		candidateCustom.setImgpath("impPippo");
 
		when(this.candidateService.getCustomById(100L) ).thenReturn(candidateCustom);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.candidateCustomController.getCandidateCustomById(100L);
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("pippo@prova.com", ((CandidateCustom) responseEntity.getBody()).getEmail());
		Assert.assertEquals("4124214124", ((CandidateCustom) responseEntity.getBody()).getMobile());
		Assert.assertEquals("pippo", ((CandidateCustom) responseEntity.getBody()).getFirstname());
		logger.info("testGetCandidateCustomById()  ---------------------- END");
	}
	
//	/**
//     * testInsertCandidateCustomSuccesfully() method tests if the method createCandidateCustom()
//     * is really able to create a new tuple in the users' table
//     */
//	@Test
//	public void testInsertCandidateCustomSuccesfully() {
//		
//		logger.info("testInsertCandidateCustomSuccesfully()  ---------------------- START");
//		
//		CandidateCustom candidateCustom = new CandidateCustom();
//		  
//		candidateCustom.setId(100L);
//		candidateCustom.setUserId(4L); 
//		candidateCustom.setDomicileCity("provaprova");  
//		candidateCustom.setDomicileStreetName("provaprovaprova");
//		candidateCustom.setMobile("4124214124");
//		candidateCustom.setEmail("pippo@prova.com"); 
//		candidateCustom.setFirstname("pippo"); 
//		candidateCustom.setLastname("prova");
//		candidateCustom.setDateOfBirth(LocalDate.of(2018, 12, 3)); 
//		candidateCustom.setImgpath("impPippo");
//		
		
//		User user = new User();
//		
//		user.setId(100L);
//		user.setEmail(candidateCustom.getEmail()); 
//		user.setFirstname(candidateCustom.getFirstname());
//		user.setLastname(candidateCustom.getLastname());
//		user.setDateOfBirth( candidateCustom.getDateOfBirth());  
//		user.setRegdate(LocalDateTime.now());
//		user.setRole(90);
//		user.setImgpath(candidateCustom.getImgpath());
// 	
//		Candidate candidate = new Candidate();
//		
//		candidate.setUserId(user.getId());
//		candidate.setDomicileCity(candidateCustom.getDomicileCity());
//		candidate.setDomicileHouseNumber(candidateCustom.getDomicileHouseNumber());
//		candidate.setDomicileStreetName(candidateCustom.getDomicileStreetName());
//		candidate.setStudyQualification(candidateCustom.getStudyQualification());
//		candidate.setGraduate(candidateCustom.getGraduate());
//		candidate.setHighGraduate(candidateCustom.getHighGraduate());
//		candidate.setStillHighStudy(candidateCustom.getStillHighStudy());
//		candidate.setMobile(candidateCustom.getMobile());
//		candidate.setCvExternalPath(candidateCustom.getCvExternalPath());
		 
//		when(this.userRepository.findByEmail(candidateCustom.getEmail())).thenReturn(null);
//		when(this.roleRepository.findByLevel(90)).thenReturn(new Role());
//		when(this.userRepository.save(user)).thenReturn(user);
//		when(this.candidateRepository.save(candidate)).thenReturn(candidate);
//		ResponseEntity<CeReProAbstractEntity> responseEntity = candidateCustomController.createCandidateCustom(candidateCustom);
//		
//		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//		Assert.assertEquals("pippo@prova.com", ((CandidateCustom) responseEntity.getBody()).getEmail()); 
//		Assert.assertEquals("4124214124", ((CandidateCustom) responseEntity.getBody()).getMobile()); 
//		Assert.assertEquals("pippo", ((CandidateCustom) responseEntity.getBody()).getFirstname());
//		Assert.assertEquals("prova", ((CandidateCustom) responseEntity.getBody()).getLastname());
//		Assert.assertEquals(LocalDate.of(2018, 12, 3), ((CandidateCustom) responseEntity.getBody()).getDateOfBirth());  
//		Assert.assertEquals("impPippo", ((CandidateCustom) responseEntity.getBody()).getImgpath());
//		logger.info("testInsertCandidateCustomSuccesfully()  ---------------------- END");
//	}
	
//	/**
//     * testInsertUserKOForEmail() method tests if the method createUser() 
//     * is really able for CONSTRAINT uniqueEmail UNIQUE (email)
//     *  
//     */
//	@Test
//	public void testInsertUserKOForEmail() {
//		
//		logger.info("testInsertUserKOForEmail()  ---------------------- START");
//		User user = new User();
//		user.setId(100L);
//		user.setEmail("pippo@prova.com");
//		user.setPassword("pippo");
//		user.setFirstname("pippo");
//		user.setLastname("prova");
//		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
//		user.setRegdate(LocalDateTime.now());
//		user.setRole(10);
//		user.setImgpath("impPippo");
//		
//		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
//		when(this.userRepository.findByEmail(user.getEmail())).thenReturn(user);
//		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);
//		
//		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
//		logger.info("testInsertUserKOForEmail()  ---------------------- END");
//	}
	
//	/**
//     * testInsertUserKOForRole() method tests if the method createUser() 
//     * is really able for foreign key (role) references roles(level)
//     *  
//     */
//	@Test
//	public void testInsertUserKOForRole() {
//		
//		logger.info("testInsertUserKOForRole()  ---------------------- START");
//		User user = new User();
//		user.setId(100L);
//		user.setEmail("pippo@prova.com");
//		user.setPassword("pippo");
//		user.setFirstname("pippo");
//		user.setLastname("prova");
//		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
//		user.setRegdate(LocalDateTime.now());
//		user.setRole(10);
//		user.setImgpath("impPippo");
//		
//		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(null);
//		ResponseEntity<CeReProAbstractEntity> responseEntity = userController.createUser(user);
//		
//		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
//		logger.info("testInsertUserKOForRole()  ---------------------- END");
//	}
	
//	/**
//     * testUpdateUserSuccessfully() method tests if the method updateUser()
//     * is really able to update fields in the users' table
//     */
//	@Test
//	public void testUpdateUserSuccessfully() {
//		
//		logger.info("testUpdateUserSuccessfully()  ---------------------- START");
//		User user = new User();
//		user.setId(100L);
//		user.setEmail("pippo@prova.com");
//		user.setPassword("pippo");
//		user.setFirstname("pippo");
//		user.setLastname("prova");
//		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
//		user.setRegdate(LocalDateTime.now());
//		user.setRole(10);
//		user.setImgpath("impPippo");
//		
//		Optional<User> currOpt = Optional.of(user) ;
//		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(new Role());
//		when(this.userRepository.findById(100L)).thenReturn(currOpt);
//		user.setPassword("pippoUPDATED");
//		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.updateUser(100L, user);
//		
//		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		Assert.assertEquals("pippoUPDATED", ((User)responseEntity.getBody()).getPassword());
//		logger.info("testUpdateUserSuccessfully()  ---------------------- END");
//	}
	
//	/**
//     * testUpdateUserKOForRole() method tests if the method udateUser() 
//     * is really able for foreign key (role) references roles(level)
//     *  
//     */
//	@Test
//	public void testUpdateUserKOForRole() {
//		
//		logger.info("testUpdateUserKOForRole()  ---------------------- START");
//		User user = new User();
//		user.setId(100L);
//		user.setEmail("pippo@prova.com");
//		user.setPassword("pippo");
//		user.setFirstname("pippo");
//		user.setLastname("prova");
//		user.setDateOfBirth(LocalDate.of(2018, 12, 3));
//		user.setRegdate(LocalDateTime.now());
//		user.setRole(10);
//		user.setImgpath("impPippo");
//		
//		Optional<User> currOpt = Optional.of(user) ;
//		when(this.roleRepository.findByLevel(user.getRole())).thenReturn(null);
//		when(this.userRepository.findById(100L)).thenReturn(currOpt);
//		user.setPassword("pippoUPDATED");
//		ResponseEntity<CeReProAbstractEntity> responseEntity = this.userController.updateUser(100L, user);
//		
//		Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
//		logger.info("testUpdateUserKOForRole()  ---------------------- END");
//	}
	 
	/**
     * teardown() method sets userController to null
     */
	@After
	public void teardown() {
		candidateCustomController = null;
	}
}