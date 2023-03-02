package centauri.academy.cerepro.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

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
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.UserTokenSurvey;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.usersurveytoken.UserSurveyTokenRepository;
import centauri.academy.cerepro.rest.response.ResponseInterview;
import centauri.academy.cerepro.rest.response.ResponseQuestion;
import centauri.academy.cerepro.rest.response.StartSurveyResponse;
import centauri.academy.cerepro.service.SurveyService;

/**
 * @author Marco Fulgosi
 *
 */

@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController {

	public static final Logger logger = LoggerFactory.getLogger(SurveyController.class);
	

	@Autowired
	private SurveyRepository surveyRepository;
	@Autowired
	private UserSurveyTokenRepository userSurveyTokenRepository;
	@Autowired
	private SurveyService surveyService = new SurveyService();

	@Autowired
	public void setSurveyJpaRepository(SurveyRepository surveyRepository) {
		this.surveyRepository = surveyRepository;
	}
	
	/****** SELECT-ALL ******/
	@GetMapping("/")
	public ResponseEntity<List<Survey>> listAllSurvey() {
		List<Survey> survey = surveyRepository.findAll();
		
		if (survey.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(survey, HttpStatus.OK);
	}
	
	
	/****** INSERT ******/
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createSurvey(@Valid @RequestBody final Survey survey) {
		
		logger.info("Creating survey : {}", survey);
		surveyRepository.save(survey);
		return new ResponseEntity<>(survey, HttpStatus.CREATED);
	}
	
	/****** SELECT BY ID ******/
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getSurveyById(@PathVariable("id") final Long id) {
	Optional<Survey> survey = surveyRepository.findById(id);
	
	if (!survey.isPresent()) {
	
	return new ResponseEntity<>(new CustomErrorType("Survey with id " + id + " not found"),HttpStatus.NOT_FOUND);
	}
	
	return new ResponseEntity<>(survey.get(), HttpStatus.OK);
	}
	
	
	/****** UPDATE ******/
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateSurvay(@PathVariable("id") final Long id, @RequestBody Survey survey) {

		// fetch user based on id and set it to currentUser object of type UserDTO

		Optional<Survey> currentSurvey = surveyRepository.findById(id);

		if (!currentSurvey.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		// update currentUser object data with user object data

		currentSurvey.get().setLabel(survey.getLabel());
		currentSurvey.get().setDescription(survey.getDescription());
		currentSurvey.get().setTime(survey.getTime());
		
		// save currentUser obejct

		surveyRepository.saveAndFlush(currentSurvey.get());

		// return ResponseEntity object

		return new ResponseEntity<>(currentSurvey.get(), HttpStatus.OK);
	}


	/****** DELETE ******/
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteSurvey(@PathVariable("id") final Long id) {
		Optional<Survey> survey = surveyRepository.findById(id);
		if (!survey.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. SurveyReply with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		surveyRepository.delete(survey.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	/**
	 * Utilizzato inizialmente al posto del token in modo da recuperare tutte le question che 
	 * l'utente deve rispondere.
	 * @param id
	 * @return
	 */
	@GetMapping("/getSurveyForCandidate/{token}")
	public ResponseEntity<StartSurveyResponse> getSurveyForCandidate(@PathVariable("token") final String token){
		logger.info("getSurveyForCandidate - START - token: " + token);
		StartSurveyResponse toSend = new StartSurveyResponse();
		/* lets take the userSurveyToken associated and check if it exists */
		UserTokenSurvey userTokenSurvey = userSurveyTokenRepository.findByGeneratedtoken(token);
		logger.info("getSurveyForCandidate - DEBUG - userTokenSurvey: " + userTokenSurvey);
		Properties props = new Properties();
		try {
			props.load(UserSurveyTokenController.class.getClassLoader().getResourceAsStream("messages.properties"));
		} catch (IOException e) {
			logger.error("Error ",e);
		}
		if(userTokenSurvey != null) {
			Optional<Survey> survey = surveyRepository.findById(userTokenSurvey.getSurveyid());
			logger.info("getSurveyForCandidate - DEBUG - survey.isPresent(): " + survey.isPresent() + " - userTokenSurvey.getSurveyid(): " + userTokenSurvey.getSurveyid());
			Boolean expired = userTokenSurvey.isExpired();
			logger.info("getSurveyForCandidate - DEBUG - userTokenSurvey.isExpired(): " + expired);
			/* check on expiration date */
			LocalDateTime expDate = userTokenSurvey.getExpirationdate();
			logger.info("getSurveyForCandidate - DEBUG - expDate.isBefore(LocalDateTime.now()): " + expDate.isBefore(LocalDateTime.now()));
			if(expDate.isBefore(LocalDateTime.now())) {
				String tokenExpired = props.getProperty("token.message.expired");
				userTokenSurvey.setExpired(true);
				userSurveyTokenRepository.saveAndFlush(userTokenSurvey);
				
				toSend.setErrorCode(2); /* if the token is expired -> errorCode = 2 */
				toSend.setExpiredToken(tokenExpired);
				return new ResponseEntity<StartSurveyResponse>(toSend, HttpStatus.OK);
			} else if(expired) {//TODO
//			if (false) {//only for testing frontend....
				toSend.setErrorCode(2); /* if the token is expired -> errorCode = 2 */
				String tokenExpired = props.getProperty("token.message.expired");
				toSend.setExpiredToken(tokenExpired);
				return new ResponseEntity<StartSurveyResponse>(toSend, HttpStatus.OK);
			} else {
					
					List<ResponseQuestion> listaResponseQuestion = surveyService.getAllRelatedQuestionsBySurveyIdOrderedByPosition(userTokenSurvey.getSurveyid());
					logger.info("getSurveyForCandidate - DEBUG - listaResponseQuestion.size(): " + listaResponseQuestion.size()); 
					toSend.setQuestions(listaResponseQuestion);
			}
			
			toSend.setSurveyId(userTokenSurvey.getSurveyid());
			toSend.setUserId(userTokenSurvey.getUserid());
			toSend.setUserTokenId(userTokenSurvey.getId());
			toSend.setTime(survey.get().getTime());
			String afterSurvey = props.getProperty("survey.message.after");
			logger.info("afterSurvey" + afterSurvey);
			toSend.setAfterSurvey(afterSurvey);
			return new ResponseEntity<StartSurveyResponse>(toSend,HttpStatus.OK);
			
			
		}
		
		/* if there's no such token -> errorCode = 1 */
		toSend.setErrorCode(1);
		String invalidToken = props.getProperty("token.message.invalid"); 
		toSend.setInvalidToken(invalidToken);
		return new ResponseEntity<StartSurveyResponse>(toSend, HttpStatus.NOT_FOUND);
	}
		
}