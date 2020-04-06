package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

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
import centauri.academy.cerepro.persistence.entity.Trainee;
import centauri.academy.cerepro.service.TraineeService;

/**
 * @author Jesus - jbm.bojorquez@gmail.com
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TraineeControllerTest {

	public static final String EMAIL_TEST = "mail@mail.com";
	public static final String NAME_TEST = "testName";
	public static final String SURNAME_TEST = "testSurname";
	public static final String PASSWORD_TEST = "testPassword";
	public static final int TEST_INT_ZERO = 0;
	public static final int TEST_INT_ONE = 1;
	public static final Long ID_TEST = 1L;

	public static final Logger logger = LoggerFactory.getLogger(TraineeControllerTest.class);

	@Spy
	private TraineeController traineeController;

	@Mock
	private TraineeService traineeService;

	@Before
	public void initialize() {

		traineeController = new TraineeController();
		ReflectionTestUtils.setField(traineeController, "traineeService", traineeService);
	}

	@Test
	public void testListAllTrainees() {
		logger.info("### testListAllTrainees - START - ###");
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(new Trainee());
		when(this.traineeService.getAll()).thenReturn(traineeList);
		ResponseEntity<List<Trainee>> responseEntity = this.traineeController.listAllTrainees();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}
	
	
	@Test
	public void testCreateTrainee() {
		logger.info("### testCreateTrainee - START - ###");
		Trainee trainee = new Trainee();
		trainee.setId(1l);
		trainee.setEmail(EMAIL_TEST);
		when(this.traineeService.getByEmail(EMAIL_TEST)).thenReturn(null);
		when(this.traineeService.insert(trainee)).thenReturn(trainee);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.createTraineeByEmail(trainee);
		Assert.assertEquals(EMAIL_TEST,((Trainee)responseEntity.getBody()).getEmail());
		Assert.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
	}
	
	@Test
	public void testGetTraineeById() {
		logger.info("### testGetTraineeById - START - ###");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		trainee.setId(ID_TEST);
		Optional<Trainee> optTrainee = Optional.of(trainee);
		when(this.traineeService.getById(ID_TEST)).thenReturn(optTrainee);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.getTraineeById(ID_TEST);
		Assert.assertEquals(ID_TEST,((Trainee)responseEntity.getBody()).getId());
		Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}
	
	@Test
	public void testDeleteTraineeById() {
		logger.info("### testDeleteTraineeById - START - ###");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		trainee.setId(ID_TEST);
		Optional<Trainee> optTrainee = Optional.of(trainee);
		when(this.traineeService.getById(ID_TEST)).thenReturn(optTrainee);
		doNothing().when(this.traineeService).deleteById(ID_TEST);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.deleteTraineeById(ID_TEST);
		Assert.assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
	}
	
	@Test
	public void testDeleteAllTrainees() {
		logger.info("### testDeleteAllTrainees - START - ###");
		Trainee trainee = new Trainee();
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(trainee);
		when(this.traineeService.getAll()).thenReturn(traineeList);
		doNothing().when(this.traineeService).deleteAll();
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.deleteAllTrainees();
		Assert.assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
	}
	
	@Test
	public void testCreateTraineeByEmail() {
		logger.info("### testCreateTraineeByEmail - START - ###");
		Trainee trainee = new Trainee();
		trainee.setId(1l);
		trainee.setEmail(EMAIL_TEST);
		when(this.traineeService.getByEmail(EMAIL_TEST)).thenReturn(null);
		when(this.traineeService.insert(trainee)).thenReturn(trainee);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.createTraineeByEmail(trainee);
		Assert.assertEquals(EMAIL_TEST,((Trainee)responseEntity.getBody()).getEmail());
		Assert.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
	}
	
	@Test
	public void testAddFormToTrainee() {
		logger.info("### testAddFormToTrainee - START - ###");
		Trainee trainee = new Trainee();
		trainee.setId(1l);
		trainee.setEmail(EMAIL_TEST);
		trainee.setFirstname(NAME_TEST);
		trainee.setLastname(SURNAME_TEST);
		trainee.setPassword(PASSWORD_TEST);
		when(this.traineeService.getById(ID_TEST)).thenReturn(Optional.of(new Trainee()));
		when(this.traineeService.update(trainee)).thenReturn(trainee);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.addFormToTrainee(ID_TEST, trainee);
		Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		Assert.assertEquals(NAME_TEST,((Trainee)responseEntity.getBody()).getFirstname());
		Assert.assertEquals(SURNAME_TEST,((Trainee)responseEntity.getBody()).getLastname());
		Assert.assertEquals(PASSWORD_TEST,((Trainee)responseEntity.getBody()).getPassword());
	}
	
	@Test
	public void testUpdateEnabledTrainee() {
		logger.info("### testUpdateEnabledTrainee - START - ###");
		Trainee trainee = new Trainee();
		trainee.setId(1l);
		trainee.setEmail(EMAIL_TEST);
		trainee.setEnabled(TEST_INT_ONE);
		when(this.traineeService.getById(ID_TEST)).thenReturn(Optional.of(new Trainee()));
		when(this.traineeService.update(trainee)).thenReturn(trainee);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.updateEnabledTrainee(ID_TEST, trainee);
		Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		Assert.assertEquals(TEST_INT_ONE,((Trainee)responseEntity.getBody()).getEnabled());
	}
	
	@Test
	public void testUpdateHasspasswordTrainee() {
		logger.info("### testUpdateHasspasswordTrainee - START - ###");
		Trainee trainee = new Trainee();
		trainee.setId(1l);
		trainee.setEmail(EMAIL_TEST);
		trainee.setHaspassword(TEST_INT_ONE);
		when(this.traineeService.getById(ID_TEST)).thenReturn(Optional.of(new Trainee()));
		when(this.traineeService.update(trainee)).thenReturn(trainee);
		ResponseEntity<CeReProAbstractEntity> responseEntity = traineeController.updateHasspasswordTrainee(ID_TEST, trainee);
		Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		Assert.assertEquals(TEST_INT_ONE,((Trainee)responseEntity.getBody()).getHaspassword());
	}
	
	@Test
	public void testListAllByEnabledTrainees() {
		logger.info("### testListAllByEnabledTrainees - START - ###");
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(new Trainee());
		when(this.traineeService.getListByEnabled()).thenReturn(traineeList);
		ResponseEntity<List<Trainee>> responseEntity = this.traineeController.listAllByEnabledTrainees();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}
	
	@Test
	public void TestListAllFilledTrainees() {
		logger.info("### TestListAllFilledTrainees - START - ###");
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(new Trainee());
		when(this.traineeService.getAllFilled()).thenReturn(traineeList);
		ResponseEntity<List<Trainee>> responseEntity = this.traineeController.listAllFilledTrainees();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}
	
	@After
	public void teardown() {
		traineeController = null;
	}

}
