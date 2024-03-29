/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.RoleRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.service.UserService;

/**
 * 
 * 
 * 
 * 
 * 
 * @author maurizio.franco@ymail.com
 * @author joffre
 * @author Orlando Platì
 * 
 *
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * getUsers method gets all users
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<User>> getUsers() {
		List<User> user = userService.getAll();
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") final String email) {

		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent())
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * getPaginatedUsers method gets all users and return only a paginated list
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/paginated/{size}/{number}/")
	public ResponseEntity<Page<User>> getPaginatedUsers(@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<User> user = userRepository.findAll(PageRequest.of(number, size, Sort.Direction.ASC, "regdate"));
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * getUserById method gets a user by id
	 * 
	 * @param id of the user to be selected
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getUserById(@PathVariable("id") final Long id) {
		Optional<User> optUser = userRepository.findById(id);

		if (!optUser.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Question with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(optUser.get(), HttpStatus.OK);
	}
	
	/**
	 * getUserByRole method gets all users by role
	 * 
	 * @param role of the user to be selected
	 * @return a new List <ResponseEntity> with the given status code
	 */
	@GetMapping("/role/{role}")
	public ResponseEntity<List<User>> getUserByRole(@PathVariable("role") final Integer role) {
		List<Optional<User>> optionalUsers = userService.getByRole(role);
		List <User> users = new ArrayList<User>();
		if (optionalUsers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		for (Optional<User> u : optionalUsers) {
			users.add(u.get());
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * createUser method creates a user
	 * 
	 * @param user to be created
	 * @return a new ResponseEntity with the given status code
	 */
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createUser(@Valid @RequestBody final User user) {
		logger.info("Creating User : {}", user);

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {

			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to create new user. A User with email " + user.getEmail() + " already exist."),
					HttpStatus.CONFLICT);
		}

		if (roleRepository.findByLevel(user.getRole()) == null) {

			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to create new user. Level " + user.getRole() + " is not present in database."),
					HttpStatus.CONFLICT);
		}

		user.setRegdate(LocalDateTime.now());
		System.out.println("user pass no cript " + user.getPassword());
		String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encoded);
		System.out.println("user pass cript " + user.getPassword());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	/**
	 * updateUser method updates a user
	 * 
	 * 
	 * WARNING THIS METHOD UPDATES ONLY EMAIL, FIRSTNAME AND LASTNAME FIELDS
	 * 
	 * 
	 * @param id   of the user to be updated
	 * @param user with the fields updated
	 * @return a new ResponseEntity with the given status code
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateUser(@PathVariable("id") final Long id, @RequestBody User user) {
		Optional<User> optUser = userRepository.findById(id);

		if (!optUser.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		if (roleRepository.findByLevel(user.getRole()) == null) {

			return new ResponseEntity<>(
					new CustomErrorType(
							"Unable to update user. Level " + user.getRole() + " is not present in database."),
					HttpStatus.CONFLICT);
		}

		// REGISTRATION DATE HAVE NOT TO BE CHANGED!!!!!!!!!!!!!!
		// so keep data from user and put all into currentUser (without regDate value!!!
//		User currentUser = optUser.get();
//		currentUser.setEmail(user.getEmail());
////		currentUser.setPassword(user.getPassword());
//		currentUser.setFirstname(user.getFirstname());
//		currentUser.setLastname(user.getLastname());
////		currentUser.setRole(user.getRole());
////		currentUser.setEnabled(user.isEnabled());

		try {
			User currentUser = optUser.get();
			currentUser.setEmail(user.getEmail());
			currentUser.setFirstname(user.getFirstname());
			currentUser.setLastname(user.getLastname());
			currentUser.setRole(user.getRole());
			userRepository.save(currentUser);
			return new ResponseEntity<>(currentUser, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType(e.getMessage())  , HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/**
	 * deleteUser method deletes a user
	 * 
	 * @param id of the user to be canceled
	 * @return a new ResponseEntity with the given status code
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteUser(@PathVariable("id") final Long id) {
		Optional<User> optUser = userRepository.findById(id);

		if (!optUser.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

//		List<UserTokenSurvey> userTokenSurvey = userSurveyTokenRepository.findByUserId(id);
//		List<SurveyReply> surveyReplies = surveyReplyRepository.findByUserId(id);

//		if (!userTokenSurvey.isEmpty()) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to delete. User with id " + id + " is userTokenSurvey referenced."),
//					HttpStatus.CONFLICT); // code 409
//
//		} else 
//			if (!surveyReplies.isEmpty()) {
//			return new ResponseEntity<>(
//					new CustomErrorType("Unable to delete. User with id " + id + " is surveyReply referenced."),
//					HttpStatus.CONFLICT); // code 409
//		} else {
			userRepository.delete(optUser.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // code 204
//		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> updateUserEnabledByid(@PathVariable("id") final Long id) {
		
		Optional<User> optUser = userService.getById(id);
		
		if (!optUser.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to update. User with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}
		
		try {
			boolean result = userService.updateEnabledById(id, !optUser.get().isEnabled());
			logger.info(""+result);
			if (result) {
				User updatedUser = userService.getById(id).get();
				updatedUser.setEnabled(!updatedUser.isEnabled());
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CustomErrorType("Unable to update. Something went wrong."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("error", e);
			return new ResponseEntity<CeReProAbstractEntity>(new CustomErrorType(e.getMessage())  , HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@PatchMapping("/updatepassword/{id}")
	public ResponseEntity<CeReProAbstractEntity> updatePasswordById(@PathVariable("id") final Long id, @RequestBody final Hashtable<String, String> passwordHashtable) {
		logger.info("updatePasswordById - START");
		Optional<User> optUser = userService.getById(id);
		logger.info("updatePasswordById - DEBUG - requesting update password for user id: {} ", id);
	    if (!optUser.isPresent()) {
	        return new ResponseEntity<>(new CustomErrorType("Unable to update. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
	    }
	    try {
	        User currentUser = optUser.get();
	        String password = passwordHashtable.get("password");
	        logger.info("updatePasswordById - DEBUG - requesting update password for user id {} with password {} to encode.", id, password);
	        String encoded = new BCryptPasswordEncoder().encode(password);
	        currentUser.setPassword(encoded);
	        User updatedUser = userRepository.save(currentUser);
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    } catch (Exception e) {
	        logger.error("error", e);
	        return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}