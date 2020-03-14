
package centauri.academy.cerepro.backend;


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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Employee;
import centauri.academy.cerepro.persistence.repository.EmployeeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

		public static final Logger logger = LoggerFactory.getLogger(EmployeeControllerTest.class);

		@Spy
		private EmployeeController employeeController;
		@Mock
		private EmployeeRepository employeeRepository;

		@Before
		public void setup() {
			employeeController = new EmployeeController();
			ReflectionTestUtils.setField(employeeController, "employeeRepository", employeeRepository);
		}

		@Test
		public void testListAllEmployees() {
			List<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(new Employee());
			when(this.employeeRepository.findAll()).thenReturn(employeeList);
			ResponseEntity<List<Employee>> responseEntity = this.employeeController.listAllEmployee();
			Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			Assert.assertEquals(1, responseEntity.getBody().size());
		}

		@Test
		public void testGetEmployeeById() {
			Long long100 = new Long(100L);
			Employee testEmployee = new Employee (100L, 10L, "milanoTest", "socrateTest","2","3458888888","cvPathTest");
			Optional<Employee> currOpt = Optional.of(testEmployee) ;
			when(this.employeeRepository.findById(100L)).thenReturn(currOpt);
			ResponseEntity<CeReProAbstractEntity> responseEntity = this.employeeController.getEmployeeById(100L);
			Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			Assert.assertEquals(long100, ((Employee)responseEntity.getBody()).getId());
			Assert.assertEquals("milanoTest", ((Employee)responseEntity.getBody()).getDomicileCity());
			Assert.assertEquals("socrateTest", ((Employee)responseEntity.getBody()).getDomicileStreetName());
			Assert.assertEquals("2", ((Employee)responseEntity.getBody()).getDomicileHouseNumber());
			Assert.assertEquals("3458888888", ((Employee)responseEntity.getBody()).getMobile());
			Assert.assertEquals("cvPathTest", ((Employee)responseEntity.getBody()).getCvExternalPath());
		}
		
		@Test
		public void testInsertEmployeeSuccessfully() {
			Long long100 = new Long(100L);
			Employee testEmployee = new Employee (100L, 10L, "milanoTest", "socrateTest","2","3458888888","cvPathTest");
			when(this.employeeRepository.save(testEmployee)).thenReturn(testEmployee);
			ResponseEntity<CeReProAbstractEntity> responseEntity = this.employeeController.createEmployee(testEmployee);
			Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
			Assert.assertEquals(long100, ((Employee)responseEntity.getBody()).getId());
			Assert.assertEquals("milanoTest", ((Employee)responseEntity.getBody()).getDomicileCity());
			Assert.assertEquals("socrateTest", ((Employee)responseEntity.getBody()).getDomicileStreetName());
			Assert.assertEquals("2", ((Employee)responseEntity.getBody()).getDomicileHouseNumber());
			Assert.assertEquals("3458888888", ((Employee)responseEntity.getBody()).getMobile());
			Assert.assertEquals("cvPathTest", ((Employee)responseEntity.getBody()).getCvExternalPath());
		}
		
		@Test
		public void testDeleteEmployeeSuccessfully() {
			Employee testEmployee = new Employee (100L, 10L, "milanoTest", "socrateTest","2","3458888888","cvPathTest");
			Optional<Employee> currOpt = Optional.of(testEmployee) ;
			when(this.employeeRepository.findById(100L)).thenReturn(currOpt);
			ResponseEntity<CeReProAbstractEntity> responseEntity = this.employeeController.deleteEmployee(100L);
			Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		}
		
		@Test
		public void testUpdateEmployeeSuccessfully() {
			Employee testEmployee = new Employee (100L, 10L, "milanoTest", "socrateTest","2","3458888888","cvPathTest");
			Optional<Employee> currOpt = Optional.of(testEmployee) ;
			when(this.employeeRepository.findById(100L)).thenReturn(currOpt);
			testEmployee.setDomicileCity("torinoUPDATED");
			ResponseEntity<CeReProAbstractEntity> responseEntity = this.employeeController.updateEmployee(100L, testEmployee);
			Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			Assert.assertEquals("torinoUPDATED", ((Employee)responseEntity.getBody()).getDomicileCity());
		}
		
		@After
		public void teardown() {
			employeeController = null;
		}
}