package centauri.academy.cerepro.integration.service;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.Trainee;
import centauri.academy.cerepro.service.TraineeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TraineeServiceIntegrationTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TraineeServiceIntegrationTest.class);
	public static final String EMAIL_TEST = "mail@mail.com";
	public static final String EMAIL_TEST_TWO = "mail_two@mail.com";
	public static final String NAME_TEST = "testName";
	public static final String SURNAME_TEST = "testSurname";
	public static final String PASSWORD_TEST = "testPassword";
	public static final int TEST_INT_ZERO = 0;
	public static final int TEST_INT_ONE = 1;
	public static final long ID_TEST = 1L;
	
	
	@Autowired
	private TraineeService tService;
	
	@Before
	public void initializeTraineeServiceTest() {
		tService.deleteAll();
	}
	@After
	public void finalizeTraineeServiceTest() {
		tService.deleteAll();
	}
	
	@Test
	public void testGetAll() {
		logger.info("testGetAll - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		tService.insert(trainee);
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(1, traineeList.size());
	}
	
	@Test
	public void testDeleteAll() {
		logger.info("testDeleteAll - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		tService.insert(trainee);
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(1, traineeList.size());
		tService.deleteAll();
		traineeList = tService.getAll();
		Assert.assertEquals(0, traineeList.size());
	}
	
	@Test
	public void testInsert() {
		logger.info("testInsert - START");
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(0, traineeList.size());
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		tService.insert(trainee);
		traineeList = tService.getAll();
		Assert.assertEquals(1, traineeList.size());
	}
	
	@Test
	public void testUpdate() {
		logger.info("testUpdate - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		// get the entity saved in database
		trainee = tService.insert(trainee);
		logger.info(trainee.getId() +  " °°°°°°########################");
		Assert.assertEquals(EMAIL_TEST,trainee.getEmail());
		trainee.setEmail(EMAIL_TEST_TWO);
		// get the entity saved in database
		trainee = tService.insert(trainee);
		Assert.assertEquals(EMAIL_TEST_TWO,trainee.getEmail());
	}
	
	@Test
	public void testGetById() {
		logger.info("testGetById - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		// get the entity saved in database
		trainee = tService.insert(trainee);
		Long id = trainee.getId();
		Optional<Trainee> optTrainee = tService.getById(id);
		trainee = optTrainee.get();
		Assert.assertEquals(id,trainee.getId());
	}
	
	@Test
	public void testDeleteById() {
		logger.info("testDeleteById - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		// get the entity saved in database
		trainee = tService.insert(trainee);
		Long id = trainee.getId();
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(1, traineeList.size());
		tService.deleteById(id);
		traineeList = tService.getAll();
		Assert.assertEquals(0, traineeList.size());
	}
	
	@Test
	public void testGetByEmail() {
		logger.info("testGetByEmail - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		// get the entity saved in database
		trainee = tService.insert(trainee);
		Optional<Trainee> optTrainee = tService.getByEmail(EMAIL_TEST);
		trainee = optTrainee.get();
		Assert.assertEquals(EMAIL_TEST, trainee.getEmail());
	}
	
	@Test
	public void testGetByEnabled() {
		logger.info("testGetByEnabled - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		trainee.setEnabled(TEST_INT_ONE);
		// get the entity saved in database
		trainee = tService.insert(trainee);
		Trainee trainee2 = new Trainee();
		trainee2.setEmail(EMAIL_TEST_TWO);
		trainee2.setEnabled(TEST_INT_ZERO);
		// get the entity saved in database
		trainee2 = tService.insert(trainee2);
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(2, traineeList.size());
		Optional<Trainee> optTrainee = tService.getByEnabled(TEST_INT_ONE);
		Assert.assertTrue(optTrainee.isPresent());
		trainee = optTrainee.get();
		Assert.assertEquals(TEST_INT_ONE, trainee.getEnabled());
	}
	
	@Test
	public void testGetAllFilled() {
		logger.info("testGetByEnabled - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		trainee.setPassword(PASSWORD_TEST);
		trainee.setFirstname(NAME_TEST);
		trainee.setLastname(SURNAME_TEST);
		trainee.setHaspassword(TEST_INT_ZERO);
		tService.insert(trainee);
		Trainee trainee2 = new Trainee();
		trainee2.setEmail(EMAIL_TEST_TWO);
		tService.insert(trainee2);
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(2, traineeList.size());
		traineeList = tService.getAllFilled();
		Assert.assertEquals(1, traineeList.size());
	}
	
	
	@Test
	public void testGetListByEnabled()
	{
		logger.info("testGetListByEnabled - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		trainee.setEnabled(TEST_INT_ZERO);
		tService.insert(trainee);
		Trainee trainee2 = new Trainee();
		trainee2.setEmail(EMAIL_TEST_TWO);
		trainee2.setEnabled(TEST_INT_ONE);
		tService.insert(trainee2);
		List<Trainee> traineeList = tService.getAll();
		Assert.assertEquals(2, traineeList.size());
		traineeList = tService.getListByEnabled();
		Assert.assertEquals(1, traineeList.size());
	}
}
