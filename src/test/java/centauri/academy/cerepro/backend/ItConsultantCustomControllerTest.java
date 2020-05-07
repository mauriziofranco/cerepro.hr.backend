package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import centauri.academy.cerepro.persistence.entity.custom.ItConsultantCustom;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.itconsultant.ItConsultantRepository;

/**
 * Unit test for ItConsultantCustomControllerTest
 * @author joffre
 * @author m.franco@proximainformatica.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest (classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.MOCK)
public class ItConsultantCustomControllerTest {
	
	public static final Logger logger = LoggerFactory.getLogger(ItConsultantCustomControllerTest.class);
	
	@Spy
	private ItConsultantCustomController itConsultantCustomController;
	@Mock
	private ItConsultantRepository itConsultantRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	  
	/**
     * setup method prepares an instance of UserController and injects the mock ItConsultantRepository UserRepository,RoleRepository  
     *   
     * using Spring’s ReflectionTestUtils utility class by calling its setField method.
     */
	@Before
	public void setup() {
		itConsultantCustomController = new ItConsultantCustomController();
 		ReflectionTestUtils.setField(itConsultantCustomController, "itConsultantRepository", itConsultantRepository);  
		ReflectionTestUtils.setField(itConsultantCustomController, "userRepository", userRepository);
		ReflectionTestUtils.setField(itConsultantCustomController, "roleRepository", roleRepository);
 		
	}
	
	/**
     * testGetAllItConsultantCustom() method tests if the method getAllItConsultantCustom()
     * is really able to select all tuples from the itConsultants' and user table
     */
	@Test
	public void testGetAllItConsultantCustom() {
		
		logger.info("testGetAllItConsultantCustom()  ---------------------- START");
		List<ItConsultantCustom> itConsultantCustomList = new ArrayList<ItConsultantCustom>();
		itConsultantCustomList.add(new ItConsultantCustom());
		
		when(this.itConsultantRepository.getAllCustomItConsultants()).thenReturn(itConsultantCustomList);
		ResponseEntity<List<ItConsultantCustom>> responseEntity = this.itConsultantCustomController.getAllItConsultantCustom();
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
		logger.info("testGetAllItConsultantCustom()  ---------------------- END");
	}
	
	/**
     * testGetItConsultantCustomById() method tests if the method getItConsultantCustomById()
     * is really able to select a tuple from the itConsultants' and users table based on the Id passed as parameter
     */ 
	@Test
	public void testGetItConsultantCustomById() {
		
		logger.info("testGetItConsultantCustomById()  ---------------------- START");
		ItConsultantCustom itConsultantCustom = new ItConsultantCustom();
	  
		itConsultantCustom.setId(100L);
		itConsultantCustom.setUserId(4L); 
		itConsultantCustom.setDomicileCity("provaprova");  
		itConsultantCustom.setDomicileStreetName("provaprovaprova");
		itConsultantCustom.setMobile("4124214124");
		itConsultantCustom.setEmail("pippo@prova.com"); 
		itConsultantCustom.setFirstname("pippo"); 
		itConsultantCustom.setLastname("prova");
		itConsultantCustom.setDateOfBirth(LocalDate.of(2018, 12, 3)); 
		itConsultantCustom.setImgpath("impPippo");
 
		when(this.itConsultantRepository.getSingleCustomItConsultant(100L) ).thenReturn(itConsultantCustom);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.itConsultantCustomController.getItConsultantCustomById(100L);
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("pippo@prova.com", ((ItConsultantCustom) responseEntity.getBody()).getEmail());
		Assert.assertEquals("4124214124", ((ItConsultantCustom) responseEntity.getBody()).getMobile());
		Assert.assertEquals("pippo", ((ItConsultantCustom) responseEntity.getBody()).getFirstname());
		logger.info("testGetItConsultantCustomById()  ---------------------- END");
	}
	
//	/**
//     * testInsertItConsultantCustomSuccesfully() method tests if the method createItConsultantCustom()
//     * is really able to create a new tuple in the users' table
//     */
//	@Test
//	public void testInsertItConsultantCustomSuccesfully() {
//		
//		logger.info("testInsertItConsultantCustomSuccesfully()  ---------------------- START");
//		
//		ItConsultantCustom itConsultantCustom = new ItConsultantCustom();
//		  
//		itConsultantCustom.setId(100L);
//		itConsultantCustom.setUserId(4L); 
//		itConsultantCustom.setDomicileCity("provaprova");  
//		itConsultantCustom.setDomicileStreetName("provaprovaprova");
//		itConsultantCustom.setMobile("4124214124");
//		itConsultantCustom.setEmail("pippo@prova.com"); 
//		itConsultantCustom.setFirstname("pippo"); 
//		itConsultantCustom.setLastname("prova");
//		itConsultantCustom.setDateOfBirth(LocalDate.of(2018, 12, 3)); 
//		itConsultantCustom.setImgpath("impPippo");
//		
		
//		User user = new User();
//		
//		user.setId(100L);
//		user.setEmail(itConsultantCustom.getEmail()); 
//		user.setFirstname(itConsultantCustom.getFirstname());
//		user.setLastname(itConsultantCustom.getLastname());
//		user.setDateOfBirth( itConsultantCustom.getDateOfBirth());  
//		user.setRegdate(LocalDateTime.now());
//		user.setRole(90);
//		user.setImgpath(itConsultantCustom.getImgpath());
// 	
//		ItConsultant itConsultant = new ItConsultant();
//		
//		itConsultant.setUserId(user.getId());
//		itConsultant.setDomicileCity(itConsultantCustom.getDomicileCity());
//		itConsultant.setDomicileHouseNumber(itConsultantCustom.getDomicileHouseNumber());
//		itConsultant.setDomicileStreetName(itConsultantCustom.getDomicileStreetName());
//		itConsultant.setStudyQualification(itConsultantCustom.getStudyQualification());
//		itConsultant.setGraduate(itConsultantCustom.getGraduate());
//		itConsultant.setHighGraduate(itConsultantCustom.getHighGraduate());
//		itConsultant.setStillHighStudy(itConsultantCustom.getStillHighStudy());
//		itConsultant.setMobile(itConsultantCustom.getMobile());
//		itConsultant.setCvExternalPath(itConsultantCustom.getCvExternalPath());
		 
//		when(this.userRepository.findByEmail(itConsultantCustom.getEmail())).thenReturn(null);
//		when(this.roleRepository.findByLevel(90)).thenReturn(new Role());
//		when(this.userRepository.save(user)).thenReturn(user);
//		when(this.itConsultantRepository.save(itConsultant)).thenReturn(itConsultant);
//		ResponseEntity<CeReProAbstractEntity> responseEntity = itConsultantCustomController.createItConsultantCustom(itConsultantCustom);
//		
//		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//		Assert.assertEquals("pippo@prova.com", ((ItConsultantCustom) responseEntity.getBody()).getEmail()); 
//		Assert.assertEquals("4124214124", ((ItConsultantCustom) responseEntity.getBody()).getMobile()); 
//		Assert.assertEquals("pippo", ((ItConsultantCustom) responseEntity.getBody()).getFirstname());
//		Assert.assertEquals("prova", ((ItConsultantCustom) responseEntity.getBody()).getLastname());
//		Assert.assertEquals(LocalDate.of(2018, 12, 3), ((ItConsultantCustom) responseEntity.getBody()).getDateOfBirth());  
//		Assert.assertEquals("impPippo", ((ItConsultantCustom) responseEntity.getBody()).getImgpath());
//		logger.info("testInsertItConsultantCustomSuccesfully()  ---------------------- END");
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
		itConsultantCustomController = null;
	}
}