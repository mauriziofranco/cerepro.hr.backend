package centauri.academy.cerepro.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.validation.Valid;

import org.proxima.common.mail.MailUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CandidateSurveyToken;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;
import centauri.academy.cerepro.persistence.repository.candidatesurveytoken.CandidateSurveyTokenRepository;
import centauri.academy.cerepro.util.RandomTokenGenerator;

/**
 * controller class for UserSurveyTokenController entity
 * 
 * @author marcov
 * @author Orlando Platì
 * @author maurizio.franco@ymail.com
 */

@RestController
@RequestMapping("/api/v1/candidatesurveytoken")
public class CandidateSurveyTokenController {
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateSurveyTokenController.class);
	
	@Value("${app.runtime.environment}")
	private String runtimeEnvironment ;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private CandidateSurveyTokenRepository candidateSurveyTokenRepository;
	
	

	@GetMapping("/")
	public ResponseEntity<List<CandidateSurveyToken>> listAllQuestions() {
		List<CandidateSurveyToken> usts = candidateSurveyTokenRepository.findAll();
		if (usts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usts, HttpStatus.OK);
	}
	
	// method to get question by id
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getCandidateSurveyTokenById(@PathVariable("id") final Long id) {
		Optional<CandidateSurveyToken> ustQ = candidateSurveyTokenRepository.findById(id);
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
		Optional<CandidateSurveyToken> ust = candidateSurveyTokenRepository.findById(id);
		if (!ust.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. User survey token with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		candidateSurveyTokenRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createCandidateSurveyToken(
			@Valid @RequestBody final CandidateSurveyToken candidatesurveytoken) {
		
		if (candidatesurveytoken.getCandidateId()==null) {
			return new ResponseEntity<>(new CustomErrorType("Inserire un candidato."), HttpStatus.CONFLICT);
		}
		
		if (candidatesurveytoken.getSurveyId()==null) {
			return new ResponseEntity<>(new CustomErrorType("Inserire un questionario."), HttpStatus.CONFLICT);
		}
		
		if (candidatesurveytoken.getExpirationDateTime()==null) {
			return new ResponseEntity<>(new CustomErrorType("Inserire una data di scadenza."), HttpStatus.CONFLICT);
		}else if(candidatesurveytoken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
			return new ResponseEntity<>(new CustomErrorType("Inserire una data di scadenza posteriore a quella attuale."), HttpStatus.CONFLICT);
		}
		
		candidatesurveytoken.setGeneratedToken(RandomTokenGenerator.generate());
		logger.info("Creating CandidateSurveyToken : {}", candidatesurveytoken);
		candidatesurveytoken.setExpired(false);
		
		candidateSurveyTokenRepository.save(candidatesurveytoken);
		return new ResponseEntity<>(candidatesurveytoken, HttpStatus.CREATED);
	}
	
	/**
	 * sendEmail method sends an email to the candidate with a link to a page with a survey
	 * @param id of the userSurveyToken
	 * @return true if the email has been correctly sent
	 * @author giacomo
	 */ 
	@GetMapping("/sendEmail/{id}")
	public ResponseEntity<Boolean> sendEmail(@PathVariable("id") final Long id) {
		logger.info("sendEmail - START - id: " + id);
		CandidateSurveyToken uts = null;
		Optional<CandidateSurveyToken> ustQ = candidateSurveyTokenRepository.findById(id);
		if (!ustQ.isPresent()) {
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		} else {
			uts=ustQ.get();
			Optional<Candidate> u = candidateRepository.findById(uts.getCandidateId());
				
			//Struttura temporanea
			if(u.isPresent()) {
				Properties props = new Properties();
				try {
					props.load(CandidateSurveyTokenController.class.getClassLoader().getResourceAsStream("messages.properties"));
				} catch (IOException e) {
					//logger.error(e);;
				}
				String messageBody=props.getProperty("mail.survey.messageBody");
				String link = props.getProperty("mail.survey.link");
				link=link.replaceAll("XXX", uts.getGeneratedToken());
				link=link.replaceAll("YYY", runtimeEnvironment);
				String subject = props.getProperty("mail.survey.subject");
				String signature = props.getProperty("mail.survey.signature");
				String message = messageBody+link+signature;
				logger.info("sendEmail - DEBUG - email: " + u.get().getEmail());
				logger.info("sendEmail - DEBUG - subject: " + subject);
				logger.info("sendEmail - DEBUG - message: " + message);
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
