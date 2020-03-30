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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.Trainee;
import centauri.academy.cerepro.service.TraineeService;

/**
 * 
 * @author Jesus Bojorquez - jbm.bojorquez@gmail.com
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/trainee")
public class TraineeController {
	public static final int CONST_ZERO = 0;
	public static final int CONST_ONE = 1;
	public static final Logger logger = LoggerFactory.getLogger(TraineeController.class);

	@Autowired
	private TraineeService traineeService;

	// use service.getAll() - get all Trainees ****
	/**
	 * listAllTrainees method gets all trainees
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<Trainee>> listAllTrainees() {
		List<Trainee> trainees = traineeService.getAll();
		if (trainees.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(trainees, HttpStatus.OK);
	}
	
	
	// use insert() - POST - insert a Trainee entity which contains an email ****
	/**
	 * createTraineeByEmail creates a new Trainee entity with a given email 
	 * 
	 * @return a boolean
	 */
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createTraineeByEmail(@Valid @RequestBody final Trainee trainee) {
		logger.info("Creating Trainee : {}", trainee);
		if (traineeService.getByEmail(trainee.getEmail()) != null) {
			logger.error("Unable to create. A Trainee with email {} already exist",
					trainee.getEmail());
			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to create new Trainee. A Trainee with email " + trainee.getEmail() + " already exist."),
					HttpStatus.CONFLICT);
		}
		traineeService.insert(trainee);
		return new ResponseEntity<CeReProAbstractEntity>(trainee, HttpStatus.CREATED);
	}
	
	
	//use update() - PUT - update with name, surname and password ****
	/**
	 * updateFormTrainee updates a Trainee with given name, surname and password and set
	 * enabled to zero
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/addForm/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> addFormToTrainee(@PathVariable("id") final Long id, @RequestBody Trainee trainee) {
		// fetch Trainee based on id and set it to optTrainee object of type Optional<Trainee>
		Optional<Trainee> optTrainee = traineeService.getById(id);
		// check if Trainee object exist in the database
		if (optTrainee.isEmpty()) {
			return new ResponseEntity<>( 
				   new CustomErrorType("Unable to update. Trainee with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		// update Trainee Object
		Trainee traineeOld = optTrainee.get();
		trainee.setEmail(traineeOld.getEmail());
		trainee.setEnabled(CONST_ONE);// repetido?
		trainee.setId(id);
		// save trainee object
		traineeService.update(trainee);
		// return ResponseEntity object
		return new ResponseEntity<>(trainee, HttpStatus.OK);
	}
	
	
	//use update() - PUT - set enable to one ****@@@@@@@@@
	/**
	 * updateEnabledTrainee set the enabled property to one
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/enableTrainee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateEnabledTrainee(@PathVariable("id") final Long id, @RequestBody Trainee trainee) {
		// fetch Trainee based on id and set it to optTrainee object of type Optional<Trainee>
		Optional<Trainee> optTrainee = traineeService.getById(id);
		// check if Trainee object exist in the database
		if (optTrainee.isEmpty()) {
			return new ResponseEntity<>( 
				   new CustomErrorType("Unable to update. Trainee with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		// update Trainee Object
		Trainee traineeOld = optTrainee.get();
		traineeOld.setEnabled(CONST_ONE);
		// save trainee object
		traineeService.update(traineeOld);
		// return ResponseEntity object
		return new ResponseEntity<>(traineeOld, HttpStatus.OK);
	}
	
	
	//use update() - PUT - set hasPassword to one ****@@@@@@@@@
	/**
	 * updateHasPasswordTrainee set the hasPassword property to one
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/setHasspasswordTrainee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateHasspasswordTrainee(@PathVariable("id") final Long id, @RequestBody Trainee trainee) {
		// fetch Trainee based on id and set it to optTrainee object of type Optional<Trainee>
		Optional<Trainee> optTrainee = traineeService.getById(id);
		// check if Trainee object exist in the database
		if (optTrainee.isEmpty()) {
			return new ResponseEntity<>( 
				   new CustomErrorType("Unable to update. Trainee with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		// update Trainee Object
		Trainee traineeOld = optTrainee.get();
		traineeOld.setHaspassword(CONST_ONE);
		// save trainee object
		traineeService.update(traineeOld);
		// return ResponseEntity object
		return new ResponseEntity<>(traineeOld, HttpStatus.OK);
	}
	
	
	//use getByEnable() - GET - get a list of Trainees - ****
	/**
	 * listAllByEnabledTrainees method gets all trainees in which enabled is set to zero
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/waitForEnabled")
	public ResponseEntity<List<Trainee>> listAllByEnabledTrainees() {
		List<Trainee> trainees = traineeService.getListByEnabled();
		if (trainees.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(trainees, HttpStatus.OK);
	}
	
	
	//use getAllFilled() - GET - get a list of trainees ****
	/**
	 * listAllTrainees method get a list of trainees that has data inside, enable=1,haspassword=0
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/waitForPassword")
	public ResponseEntity<List<Trainee>> listAllFilledTrainees() {
		List<Trainee> trainees = traineeService.getAllFilled();
		if (trainees.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(trainees, HttpStatus.OK);
	}
	
}
