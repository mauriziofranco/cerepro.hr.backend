/**
 * 
 */
package centauri.academy.cerepro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import centauri.academy.cerepro.persistence.entity.Question;
import centauri.academy.cerepro.persistence.repository.question.QuestionRepository;
import centauri.academy.cerepro.rest.request.InterviewReplyRequest;
import centauri.academy.cerepro.rest.request.SingleQuestionReplyRequest;

/**
 * @author Marco Fulgosi
 *
 */
@Service
public class SurveyReplyRequestService {
	
	public static final Logger logger = LoggerFactory.getLogger(SurveyReplyRequestService.class);
	
	@Autowired
	private QuestionRepository questionRepository;
	
	private static final Long CORRECT_ANSWER = 2L;
	private static final Long WRONG_ANSWER = -1L; 
	
	public Long getCorrect() {
		return CORRECT_ANSWER;
	}

//	public Long pointsCalculator(List<SingleQuestionReplyRequest> answers) {
//		Long punti = 0L;
//		for(SingleQuestionReplyRequest sqr : answers) {
//				Question q = questionRepository.getOne(sqr.getQuestionId());
//				if(q.getCansa()!=null && sqr.getCansa()!=null) {
//					if (q.getCansa()==sqr.getCansa()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCansb()!=null && sqr.getCansb()!=null) {
//					if (q.getCansb()==sqr.getCansb()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCansc()!=null && sqr.getCansc()!=null) {
//					if (q.getCansc()==sqr.getCansc()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCansd()!=null && sqr.getCansd()!=null) {
//					if (q.getCansd()==sqr.getCansd()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCanse()!=null && sqr.getCanse()!=null) {
//					if (q.getCanse()==sqr.getCanse()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCansf()!=null && sqr.getCansf()!=null) {
//					if (q.getCansf()==sqr.getCansf()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCansg()!=null && sqr.getCansg()!=null) {
//					if (q.getCansg()==sqr.getCansg()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//				if(q.getCansh()!=null && sqr.getCansh()!=null) {
//					if (q.getCansh()==sqr.getCansh()) punti += CORRECT_ANSWER;
//					else punti += WRONG_ANSWER;
//					}
//			}
//		return punti;
//	}
	
	public String calculateScore(List<SingleQuestionReplyRequest> answers) {
		logger.info("calculateScore - START");
		Long minRangeScore = 0l ;
		Long maxRangeScore = 0l ;
		Long score = 0L;
		for(SingleQuestionReplyRequest sqr : answers) {
				Question q = questionRepository.getOne(sqr.getQuestionId());
				if(q.getCansa()!=null && sqr.getCansa()!=null) {
					if (q.getCansa()==sqr.getCansa()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCansb()!=null && sqr.getCansb()!=null) {
					if (q.getCansb()==sqr.getCansb()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCansc()!=null && sqr.getCansc()!=null) {
					if (q.getCansc()==sqr.getCansc()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCansd()!=null && sqr.getCansd()!=null) {
					if (q.getCansd()==sqr.getCansd()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCanse()!=null && sqr.getCanse()!=null) {
					if (q.getCanse()==sqr.getCanse()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCansf()!=null && sqr.getCansf()!=null) {
					if (q.getCansf()==sqr.getCansf()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCansg()!=null && sqr.getCansg()!=null) {
					if (q.getCansg()==sqr.getCansg()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
				if(q.getCansh()!=null && sqr.getCansh()!=null) {
					if (q.getCansh()==sqr.getCansh()) score += CORRECT_ANSWER;
					else score += WRONG_ANSWER;
					}
			}
		logger.info("calculateScore - END - score: " + score);
		return score+"";
	}
	
	public String answersToString(List<SingleQuestionReplyRequest> answers) {
		Gson gson = new Gson();
		return gson.toJson(answers);
	}
	
	public String answersInterviewToString(List<InterviewReplyRequest> answers) {
		Gson gson = new Gson();
		return gson.toJson(answers);
	}
	
}
