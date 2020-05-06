package centauri.academy.cerepro.backend;

import java.util.List;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.custom.UserSurveyTokenCustom;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;

/**
 * controller class for UserSurveyTokenCustom entity
 * 
 * @author joffre
 * @author Orlando Platì
 */

@RestController
@RequestMapping("/api/v1/usersurveytokencustom")
public class UserSurveyTokenCustomController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserSurveyTokenCustomController.class);
	
	@Autowired
	private UserSurveyTokenRepository userSurveyTokenRepository;
	
	/**
	 * listAllUserSurveyToken method gets all UserSurveyTokenCustom
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<UserSurveyTokenCustom>> listAllUserSurveyToken() {
		List<UserSurveyTokenCustom> userSurveyTokenList = userSurveyTokenRepository.getAllCustomUserTokenSurvey();
		if (userSurveyTokenList.isEmpty()) {                
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else
			return new ResponseEntity<>(userSurveyTokenList, HttpStatus.OK);
	}
	
	/******* PAGEABLE ********/
	/**
	 * 
	 * @author giacomo
	 * @author daniele
	 */
	@GetMapping("/expired/{size}/{number}/")
	public ResponseEntity<Page<UserSurveyTokenCustom>> getExpiredUserSurveyToken(
			@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<UserSurveyTokenCustom> cC = userSurveyTokenRepository.getAllCustomUserTokenSurveyPaginated(PageRequest.of(number, size, Sort.Direction.ASC, "id"), true);
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cC, HttpStatus.OK);
	}
	
	/******** PAGEABLE *******/
	
	/******* PAGEABLE ********/
	/**
	 * 
	 * @author giacomo
	 * @author daniele
	 */
	@GetMapping("/active/{size}/{number}/")
	public ResponseEntity<Page<UserSurveyTokenCustom>> getActiveUserSurveyToken(
			@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<UserSurveyTokenCustom> cC = userSurveyTokenRepository.getAllCustomUserTokenSurveyPaginated(PageRequest.of(number, size, Sort.Direction.ASC, "id"), false);
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cC, HttpStatus.OK);
	}
	
	/******** PAGEABLE *******/
	
//	/**
//	 * listAllUserSurveyToken method gets all expired UserSurveyTokenCustom
//	 * @return a new ResponseEntity with the given status code
//	 */
//	@GetMapping("/expired/")
//	public ResponseEntity<List<UserSurveyTokenCustom>> listAllUserSurveyTokenExpired() {
//		List<UserSurveyTokenCustom> userSurveyTokenList = userSurveyTokenRepository.getAllCustomUserTokenSurveyExpired();
//		if (userSurveyTokenList.isEmpty()) {                
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}else
//			return new ResponseEntity<>(userSurveyTokenList, HttpStatus.OK);
//	}
//	
//	/**
//	 * listAllUserSurveyToken method gets all active UserSurveyTokenCustom
//	 * @return a new ResponseEntity with the given status code
//	 */
//	@GetMapping("/active/")
//	public ResponseEntity<List<UserSurveyTokenCustom>> listAllUserSurveyTokenActive() {
//		List<UserSurveyTokenCustom> userSurveyTokenList = userSurveyTokenRepository.getAllCustomUserTokenSurveyActive();
//		if (userSurveyTokenList.isEmpty()) {                
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}else
//			return new ResponseEntity<>(userSurveyTokenList, HttpStatus.OK);
//	}
}