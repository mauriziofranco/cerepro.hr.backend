package centauri.academy.cerepro.backend;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.SurveyQuestionCustom;
import centauri.academy.cerepro.persistence.repository.surveyquestion.SurveyQuestionRepository;

/**
 * controller class for UserSurveyTokenCustom entity
 * 
 * @author joffre
 * @author Orlando Platì
 */

@RestController
@RequestMapping("/api/v1/surveyquestioncustom")
public class SurveyQuestionCustomController {
	
	public static final Logger logger = LoggerFactory.getLogger(SurveyQuestionCustomController.class);
	
	@Autowired
	private SurveyQuestionRepository surveyQuestionRepository;
	
	/**
	 * listAllSurveyQuestion method gets all SurveyQuestionCustom
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<SurveyQuestionCustom>> listAllSurveyQuestion() {
		List<SurveyQuestionCustom> surveyQuestionList = surveyQuestionRepository.getAllCustomSurveyQuestion();
		if (surveyQuestionList.isEmpty()) {                
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else
			return new ResponseEntity<>(surveyQuestionList, HttpStatus.OK);
	}
	
}