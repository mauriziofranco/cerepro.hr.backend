/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Employee;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.EmployeeRepository;

/**
 * @author Dario
 * @author Orlando Platì
 *
 */

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	private EmployeeRepository employeeRepository;

	@Autowired
	public void employeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	// method to get list of employees
	@GetMapping("/")

	public ResponseEntity<List<Employee>> listAllEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		if (employees.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	// method to get employee by id
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getEmployeeById(@PathVariable("id") final Long id) {
		Employee employee = null;
		Optional<Employee> e = employeeRepository.findById(id);
		if (!e.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to find Employee with id " + id),
					HttpStatus.NOT_FOUND);
		} else {
			employee = e.get();
			return new ResponseEntity<>(employee, HttpStatus.OK);

		}

	}

	// method to create an employee
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createEmployee(@Valid @RequestBody final Employee employee) {
		logger.info("Creating employee : {}", employee);
		employeeRepository.save(employee);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	// method to update an existing employee
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateEmployee(@PathVariable("id") final Long id,
			@RequestBody Employee employee) {
		Employee currentemployee = null;
		Optional<Employee> optE = employeeRepository.findById(id);

		if (optE.isPresent()) {
			currentemployee = optE.get();
			currentemployee.setUserId(employee.getUserId());
			currentemployee.setDomicileCity(employee.getDomicileCity());
			currentemployee.setDomicileStreetName(employee.getDomicileStreetName());
			currentemployee.setDomicileHouseNumber(employee.getDomicileHouseNumber());
			currentemployee.setMobile(employee.getMobile());
			currentemployee.setCvExternalPath(employee.getCvExternalPath());

			employeeRepository.saveAndFlush(currentemployee);
			return new ResponseEntity<>(currentemployee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomErrorType("Unable to update. Employee with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	// delete an existing employee
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteEmployee(@PathVariable("id") final Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (!employee.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Employee with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		employeeRepository.deleteById(id);
		return new ResponseEntity<>(new CustomErrorType("Good Employee with id " + id + " is deleted."),
				HttpStatus.NO_CONTENT);

	}

}
