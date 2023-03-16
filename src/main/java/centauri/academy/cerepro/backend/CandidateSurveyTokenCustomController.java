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

import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.custom.CandidateSurveyTokenCustom;
import centauri.academy.cerepro.persistence.repository.candidatesurveytoken.CandidateSurveyTokenRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;

/**
 * controller class for CandidateSurveyTokenCustom entity
 * 
 * @author joffre
 * @author Orlando Platì
 * @author giacomo
 * @author daniele
 * @author maurizio.franco@ymail.com
 */

@RestController
@RequestMapping("/api/v1/candidatesurveytokencustom")
public class CandidateSurveyTokenCustomController {
	
	public static final Logger logger = LoggerFactory.getLogger(CandidateSurveyTokenCustomController.class);
	
	@Autowired
	private CandidateSurveyTokenRepository candidateSurveyTokenRepository;
	
	
	/**
	 * listAllUserSurveyToken method gets all CandidateSurveyTokenCustom
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<CandidateSurveyTokenCustom>> listAllUserSurveyToken() {
		List<CandidateSurveyTokenCustom> candidateSurveyTokenList = candidateSurveyTokenRepository.getAllCustomCandidateSurveyToken();
		System.out.println("DIMENSIONE LISTA CANDIDATE: " + candidateSurveyTokenList.size());
		if (candidateSurveyTokenList.isEmpty()) {                
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(candidateSurveyTokenList, HttpStatus.OK);
		}
	}
	
	/**
	 * 
	 * 
	 */
	@GetMapping("/expired/{size}/{number}/")
	public ResponseEntity<Page<CandidateSurveyTokenCustom>> getExpiredUserSurveyToken(
			@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<CandidateSurveyTokenCustom> cC = candidateSurveyTokenRepository.getAllCustomCandidateSurveyTokenExpiredPaginated(PageRequest.of(number, size, Sort.Direction.ASC, "id"), true);
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cC, HttpStatus.OK);
	}
	
	/******** PAGEABLE *******/
	
	/******* PAGEABLE ********/
	/**
	 * 
	 */
	@GetMapping("/active/{size}/{number}/")
	public ResponseEntity<Page<CandidateSurveyTokenCustom>> getActiveUserSurveyToken(
			@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<CandidateSurveyTokenCustom> cC = candidateSurveyTokenRepository.getAllCustomCandidateSurveyTokenExpiredPaginated(PageRequest.of(number, size, Sort.Direction.ASC, "id"), false);
		if (cC.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cC, HttpStatus.OK);
	}
	
}