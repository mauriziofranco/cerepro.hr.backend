/**
 * 
 */
package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.Question;
import centauri.academy.cerepro.persistence.repository.QuestionRepository;
import centauri.academy.cerepro.rest.request.SingleQuestionReplyRequest;
import centauri.academy.cerepro.service.SurveyReplyRequestService;

/**
 * @author Marco Fulgosi
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyReplyRequestServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(SurveyReplyRequestServiceTest.class);

	@Spy
	private SurveyReplyRequestService surveyReplyRequestService;
	@Mock
	private QuestionRepository questionRepository;
	@Before
	public void setup() {
		surveyReplyRequestService = new SurveyReplyRequestService();
		/* setta nellistanza del controllore dove ci sta il field che si chiama tra le "" l'istanza survreorep:  */
		ReflectionTestUtils.setField(surveyReplyRequestService, "questionRepository", questionRepository);
	}
	
	@Test
	public void testPointsCalculatorSuccessfully() {
		logger.info(" # testPointsCalculatorSuccessfully() - START");
		Question question = new Question();
		question.setLabel("testLabel");
		question.setCansa(true);
		question.setCansb(true);
		question.setCansc(true);
		question.setCansd(true);
		question.setCanse(true);
		question.setCansf(true);
		question.setCansg(true);
		question.setCansh(true);
		question.setId(1000L);
		
		SingleQuestionReplyRequest testsqr = new SingleQuestionReplyRequest();
		testsqr.setCansa(true);
		testsqr.setCansb(true);
		testsqr.setCansc(true);
		testsqr.setCansd(true);
		testsqr.setCanse(true);
		testsqr.setCansf(true);
		testsqr.setCansg(true);
		testsqr.setCansh(true);
		testsqr.setQuestionId(1000L);
		List<SingleQuestionReplyRequest> answers = new ArrayList<SingleQuestionReplyRequest>();
		answers.add(testsqr);
		
		when(this.questionRepository.getOne(question.getId())).thenReturn(question);
		
		Long punteggio = surveyReplyRequestService.pointsCalculator(answers);
		logger.info(" # # punteggio totalizzato: "+ punteggio);
		
		Long punteggioMAX = 8L*surveyReplyRequestService.getCorrect();
		logger.info(" # # punteggio massimo: "+ punteggioMAX);
		
		Assert.assertTrue(punteggioMAX == punteggio);
		logger.info(" # testPointsCalculatorSuccessfully() - END");
	}
	
	@Test
	public void testPointsCalculatorWrongAnswer() {
		logger.info(" # testPointsCalculatorWrongAnswer() - START");
		Question question = new Question();
		question.setLabel("testLabel");
		question.setCansa(true);
		question.setCansb(true);
		question.setCansc(true);
		question.setCansd(true);
		question.setCanse(true);
		question.setCansf(true);
		question.setCansg(true);
		question.setCansh(true);
		question.setId(1000L);
		
		SingleQuestionReplyRequest testsqr = new SingleQuestionReplyRequest();
		testsqr.setCansa(true);
		testsqr.setCansb(true);
		testsqr.setCansc(true);
		testsqr.setCansd(true);
		testsqr.setCanse(false);
		testsqr.setCansf(true);
		testsqr.setCansg(true);
		testsqr.setCansh(true);
		testsqr.setQuestionId(question.getId());
		List<SingleQuestionReplyRequest> answers = new ArrayList<SingleQuestionReplyRequest>();
		answers.add(testsqr);
		
		when(this.questionRepository.getOne(question.getId())).thenReturn(question);
		
		Long punteggio = surveyReplyRequestService.pointsCalculator(answers);
		logger.info(" # # punteggio totalizzato: "+ punteggio);
		
		Long punteggioMAX = 8L*surveyReplyRequestService.getCorrect();
		logger.info(" # # punteggio massimo: "+ punteggioMAX);
		
		Assert.assertTrue(punteggioMAX > punteggio);
		logger.info(" # testPointsCalculatorWrongAnswer() - END");
	}
	
	@Test
	public void testPointsCalculatorALLWrongAnswers() {
		logger.info(" # testPointsCalculatorALLWrongAnswers() - START");
		Question question = new Question();
		question.setLabel("testLabel");
		question.setCansa(true);
		question.setCansb(true);
		question.setCansc(true);
		question.setCansd(true);
		question.setCanse(true);
		question.setCansf(true);
		question.setCansg(true);
		question.setCansh(true);
		question.setId(1000L);
		
		SingleQuestionReplyRequest testsqr = new SingleQuestionReplyRequest();
		testsqr.setCansa(false);
		testsqr.setCansb(false);
		testsqr.setCansc(false);
		testsqr.setCansd(false);
		testsqr.setCanse(false);
		testsqr.setCansf(false);
		testsqr.setCansg(false);
		testsqr.setCansh(false);
		testsqr.setQuestionId(question.getId());
		List<SingleQuestionReplyRequest> answers = new ArrayList<SingleQuestionReplyRequest>();
		answers.add(testsqr);
		
		when(this.questionRepository.getOne(question.getId())).thenReturn(question);
		
		Long punteggio = surveyReplyRequestService.pointsCalculator(answers);
		logger.info(" # # punteggio totalizzato: "+ punteggio);
		
		Assert.assertTrue(-8L == punteggio);
		logger.info(" # testPointsCalculatorALLWrongAnswers() - END");
	}
	
}