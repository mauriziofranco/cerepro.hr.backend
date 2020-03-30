package centauri.academy.cerepro.service;

import static org.mockito.Mockito.when;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.Trainee;
import centauri.academy.cerepro.persistence.repository.TraineeRepository;
import centauri.academy.cerepro.service.TraineeService;

/**
 * @author Jesus - jbm.bojorquez@gmail.com
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TraineeServiceTest {

	public static final String EMAIL_TEST = "mail@mail.com";
	public static final int ENABLED_TEST = 0;
	public static final long ID_TEST = 1L;

	public static final Logger logger = LoggerFactory.getLogger(TraineeServiceTest.class);

	@Spy
	private TraineeService traineeService;

	@Mock
	private TraineeRepository traineeRepository;

	@Before
	public void initialize() {

		traineeService = new TraineeService();
		ReflectionTestUtils.setField(traineeService, "traineeRepository", traineeRepository);
	}

	@Test
	public void testListAllTrainees() {
		logger.info("testListAllTrainees - START");
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(new Trainee());
		when(this.traineeRepository.findAll()).thenReturn(traineeList);
		List<Trainee> serviceResponseList = this.traineeService.getAll();
		Assert.assertEquals(1, serviceResponseList.size());
	}

	@Test
	public void testInsertTrainee() {
		logger.info("testInsertTrainee - START");
		Trainee trainee = new Trainee();
		when(this.traineeRepository.save(trainee)).thenReturn(trainee);
		Assert.assertEquals(true, this.traineeService.insert(trainee) != null);
	}

	@Test
	public void testUpdateTrainee() {
		logger.info("testUpdateTrainee - START");
		Trainee trainee = new Trainee();
		when(this.traineeRepository.save(trainee)).thenReturn(trainee);
		Assert.assertEquals(true, this.traineeService.insert(trainee) != null);

		trainee.setEmail(EMAIL_TEST);
		Assert.assertEquals(EMAIL_TEST, traineeService.update(trainee).getEmail());
	}

	@Test
	public void testGetByIdTrainee() {
		logger.info("testGetByIdTrainee - START");
		Trainee trainee = new Trainee();
		trainee.setId(ID_TEST);
		Optional<Trainee> optTrainee = Optional.of(trainee);
		when(this.traineeRepository.findById(ID_TEST)).thenReturn(optTrainee);
		Assert.assertEquals(true, this.traineeService.getById(ID_TEST).isPresent());
	}

	@Test
	public void testGetByEmailTrainee() {
		logger.info("testGetByEmailTrainee - START");
		Trainee trainee = new Trainee();
		trainee.setEmail(EMAIL_TEST);
		Optional<Trainee> optTrainee = Optional.of(trainee);
		when(this.traineeRepository.findByEmail(EMAIL_TEST)).thenReturn(optTrainee);
		Assert.assertEquals(true, this.traineeService.getByEmail(EMAIL_TEST).get().getEmail().equals(EMAIL_TEST));
	}

	@Test
	public void testGetByEnabledTrainee() {
		logger.info("testGetByEnabledTrainee - START");
		Trainee trainee = new Trainee();
		trainee.setEnabled(ENABLED_TEST);
		Optional<Trainee> optTrainee = Optional.of(trainee);
		when(this.traineeRepository.findByEnabled(ENABLED_TEST)).thenReturn(optTrainee);
		Assert.assertEquals(true, this.traineeService.getByEnabled(ENABLED_TEST).isPresent());
	}

	@Test
	public void testGetAllFilledTrainees() {
		logger.info("testgetAllFilledTrainees - START");
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(new Trainee());
		when(this.traineeRepository.findByFilled()).thenReturn(traineeList);
		List<Trainee> serviceResponseList = this.traineeService.getAllFilled();
		Assert.assertEquals(1, serviceResponseList.size());
	}
	
	@Test
	public void testGetListByEnabledTrainees() {
		logger.info("testgetAllFilledTrainees - START");
		List<Trainee> traineeList = new ArrayList<Trainee>();
		traineeList.add(new Trainee());
		when(this.traineeRepository.findListByEnabled()).thenReturn(traineeList);
		List<Trainee> serviceResponseList = this.traineeService.getListByEnabled();
		Assert.assertEquals(1, serviceResponseList.size());
	}

	@After
	public void teardown() {
		traineeService = null;
	}

}
