package centauri.academy.cerepro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.persistence.entity.Question;
import centauri.academy.cerepro.persistence.entity.custom.QuestionCustom;
import centauri.academy.cerepro.persistence.repository.question.QuestionRepository;

/**
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@Service
public class QuestionService {

	public static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

	@Autowired
	QuestionRepository questionRepository;

	/**
	 * Provides list of question entities from repository
	 * 
	 * @return List<Question>, list of Question entity objects
	 */
	public List<Question> getAll() {
		logger.info("getAll - START");
		List<Question> items = questionRepository.findAll();
		logger.info("getAll - END - returning " + items.size() + " roles.");
		return items;
	}

	/**
	 * Provides list of question custom entities from survey id given
	 * 
	 * @return List<QuestionCustom>, list of QuestionCustom objects
	 */
	public List<QuestionCustom> getAllQuestionCustomListFromSurveyId(long surveyId) {
		logger.info("getAllQuestionCustomListFromSurveyId - START");
		List<QuestionCustom> items = questionRepository.findBySurveyId(surveyId);
		logger.info("getAllQuestionCustomListFromSurveyId - END - returning " + items.size() + " roles.");
		return items;
	}
}
