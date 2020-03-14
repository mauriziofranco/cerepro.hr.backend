package centauri.academy.cerepro.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.proxima.common.mail.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.UserTokenSurvey;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;
import centauri.academy.cerepro.util.RandomTokenGenerator;

/**
 * controller class for UserSurveyTokenController entity
 * 
 * @author marcov
 * @author Orlando Platì
 */

@RestController
@RequestMapping("/api/v1/usersurveytoken")
public class UserSurveyTokenController {
	
	public static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	private UserSurveyTokenRepository userSurveyTokenRepository;
	
	@Value("${app.runtime.environment}")
	private String runtimeEnvironment ;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public void setUserSurveyTokenRepository(UserSurveyTokenRepository userSurveyTokenRepository) {
		this.userSurveyTokenRepository = userSurveyTokenRepository;
	}

	@GetMapping("/")
	public ResponseEntity<List<UserTokenSurvey>> listAllQuestions() {
		List<UserTokenSurvey> usts = userSurveyTokenRepository.findAll();
		if (usts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usts, HttpStatus.OK);
	}
	
	// method to get question by id
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getUserTokenSurveyById(@PathVariable("id") final Long id) {
		Optional<UserTokenSurvey> ustQ = userSurveyTokenRepository.findById(id);
		if (!ustQ.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("User survey token with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(ustQ.get(), HttpStatus.OK);
		}
	}

	// delete an existing question
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteUserSurveyToken(@PathVariable("id") final Long id) {
		Optional<UserTokenSurvey> ust = userSurveyTokenRepository.findById(id);
		if (!ust.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. User survey token with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		userSurveyTokenRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createUserTokenSurvey(
			@Valid @RequestBody final UserTokenSurvey usersurveytoken) {
		
		if (usersurveytoken.getUserid()==null) {
			return new ResponseEntity<>(new CustomErrorType("Inserire un utente."), HttpStatus.CONFLICT);
		}
		
		if (usersurveytoken.getSurveyid()==null) {
			return new ResponseEntity<>(new CustomErrorType("Inserire un questionario."), HttpStatus.CONFLICT);
		}
		
		if (usersurveytoken.getExpirationdate()==null) {
			return new ResponseEntity<>(new CustomErrorType("Inserire una data di scadenza."), HttpStatus.CONFLICT);
		}else if(usersurveytoken.getExpirationdate().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(new CustomErrorType("Inserire una data di scadenza posteriore a quella attuale."), HttpStatus.CONFLICT);
		}
		
		usersurveytoken.setGeneratedtoken(RandomTokenGenerator.generate());
		logger.info("Creating UserTokenSurvey : {}", usersurveytoken);
		usersurveytoken.setExpired(false);
		
		userSurveyTokenRepository.save(usersurveytoken);
		return new ResponseEntity<>(usersurveytoken, HttpStatus.CREATED);
	}
	
	/**
	 * sendEmail method sends an email to the candidate with a link to a page with a survey
	 * @param id of the userSurveyToken
	 * @return true if the email has been correctly sent
	 * @author giacomo
	 */ 
	@GetMapping("/sendEmail/{id}")
	public ResponseEntity<Boolean> sendEmail(@PathVariable("id") final Long id) {
		UserTokenSurvey uts = null;
		Optional<UserTokenSurvey> ustQ = userSurveyTokenRepository.findById(id);
		if (!ustQ.isPresent()) {
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		} else {
			uts=ustQ.get();
			Optional<User> u = userRepository.findById(uts.getUserid());
				
			//Struttura temporanea
			if(u.isPresent()) {
				Properties props = new Properties();
				try {
					props.load(UserSurveyTokenController.class.getClassLoader().getResourceAsStream("messages.properties"));
				} catch (IOException e) {
					//logger.error(e);;
				}
				String messageBody=props.getProperty("mail.survey.messageBody");
				String link = props.getProperty("mail.survey.link");
				link=link.replaceAll("XXX", uts.getGeneratedtoken());
				link=link.replaceAll("YYY", runtimeEnvironment);
				String subject = props.getProperty("mail.survey.subject");
				String signature = props.getProperty("mail.survey.signature");
				String message = messageBody+link+signature;
				boolean mailSent = MailUtility.sendSimpleMail(u.get().getEmail(), subject, message);
				return new ResponseEntity<Boolean>(mailSent,HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
}


//update no in input e output
//generated token no in input
//tengo il generatedtoken 123456789 in output
// pulsante mail che contiene link, utente clicca e finisce su una pagina 
