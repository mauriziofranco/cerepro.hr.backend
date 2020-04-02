package centauri.academy.cerepro.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.Trainee;
import centauri.academy.cerepro.persistence.repository.CoursePageRepository;
import centauri.academy.cerepro.persistence.repository.TraineeRepository;

/**
 * 
 * 
 * @author Jesus Bojorquez - jbm.bojorquez@gmail.com
 *
 */
@Service
public class TraineeService {
	public static final Logger log = LoggerFactory.getLogger(TraineeService.class);

	@Autowired
	private TraineeRepository traineeRepository;
	@Autowired
	private CoursePageRepository coursePageRepository;

	/**
	 * Gets all Trainee entries from table
	 * 
	 * @return List<Trainee>
	 */
	public List<Trainee> getAll() {
		log.info("TraineeService.getAll - START");
		return traineeRepository.findAll();
	}
	
	/**
	 * Delete all trainees
	 * 
	 */
	public void deleteAll() {
		log.info("TraineeService.deleteAll - START");
		traineeRepository.deleteAll();
	}

	/**
	 * Insert new Trainee entity
	 * @param Trainee entity
	 * @return Trainee entity
	 */
	public Trainee insert(Trainee trainee) {
		log.info("TraineeService.insert START with given trainee {}", trainee);
		return traineeRepository.save(trainee);
	}

	/**
	 * Update Trainee entity
	 * @param Trainee entity
	 * @return Trainee entity
	 */
	public Trainee update(Trainee trainee) {
		log.info("TraineeService.update START with given trainee {}", trainee);
		return traineeRepository.save(trainee);
	}

	/**
	 * Retrieve Trainee by id
	 * 
	 * @param id of the Trainee to retrieve
	 * @return Trainee entity
	 */
	public Optional<Trainee> getById(long id) {
		log.info("TraineeService.getById START with given id {}", id);
		return traineeRepository.findById(id);
	}

	/**
	 * Delete Trainee by id
	 * 
	 * @param id of the Trainee to delete
	 */
	public void deleteById(long id) {
		log.info("TraineeService.deleteById START with given id {}", id);
		traineeRepository.deleteById(id);
	}

	/**
	 * Retrieve Trainee by email
	 * 
	 * @param email of the Trainee to retrieve
	 * @return Optional Trainee entity
	 */
	public Optional<Trainee> getByEmail(String email) {
		log.info("TraineeService.getByEmail START with given email {}", email);
		return traineeRepository.findByEmail(email);
	}

	/**
	 * Retrieve Enabled by enabled value
	 * 
	 * @param enabled value of the Trainee to retrieve
	 * @return Optional Trainee entity
	 */
	public Optional<Trainee> getByEnabled(int enabled) {
		log.info("TraineeService.getByEnabled START with given enabled value {}", enabled);
		return traineeRepository.findByEnabled(enabled);
	}

	/**
	 * Gets all Trainee entries filled with email, password, firstname, lastname and
	 * hasspassword = 0
	 * 
	 * @return List<Trainee>
	 */
	public List<Trainee> getAllFilled() {
		log.info("Trainee.getAllFilled - START");
		return traineeRepository.findByFilled();
	}
	
	/**
	 * Gets all Trainee entries filled with enabled = 0
	 * 
	 * @return List<Trainee>
	 */
	public List<Trainee> getListByEnabled() {
		log.info("Trainee.getListByEnabled - START");
		return traineeRepository.findListByEnabled();
	}
	//ramdom comment
}
