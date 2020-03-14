/**
 * 
 */
package centauri.academy.cerepro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import centauri.academy.cerepro.persistence.entity.Question;
import centauri.academy.cerepro.persistence.repository.QuestionRepository;
import centauri.academy.cerepro.rest.request.InterviewReplyRequest;
import centauri.academy.cerepro.rest.request.SingleQuestionReplyRequest;

/**
 * @author Marco Fulgosi
 *
 */
@Service
public class SurveyReplyRequestService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	private Long correct = 2L; // intero positvo
	private Long wrong = -1L; 
	
	public Long getCorrect() {
		return correct;
	}

	public Long pointsCalculator(List<SingleQuestionReplyRequest> answers) {
		Long punti = 0L;
		for(SingleQuestionReplyRequest sqr : answers) {
				Question q = questionRepository.getOne(sqr.getQuestionId());
				if(q.getCansa()!=null && sqr.getCansa()!=null) {
					if (q.getCansa()==sqr.getCansa()) punti += correct;
					else punti += wrong;
					}
				if(q.getCansb()!=null && sqr.getCansb()!=null) {
					if (q.getCansb()==sqr.getCansb()) punti += correct;
					else punti += wrong;
					}
				if(q.getCansc()!=null && sqr.getCansc()!=null) {
					if (q.getCansc()==sqr.getCansc()) punti += correct;
					else punti += wrong;
					}
				if(q.getCansd()!=null && sqr.getCansd()!=null) {
					if (q.getCansd()==sqr.getCansd()) punti += correct;
					else punti += wrong;
					}
				if(q.getCanse()!=null && sqr.getCanse()!=null) {
					if (q.getCanse()==sqr.getCanse()) punti += correct;
					else punti += wrong;
					}
				if(q.getCansf()!=null && sqr.getCansf()!=null) {
					if (q.getCansf()==sqr.getCansf()) punti += correct;
					else punti += wrong;
					}
				if(q.getCansg()!=null && sqr.getCansg()!=null) {
					if (q.getCansg()==sqr.getCansg()) punti += correct;
					else punti += wrong;
					}
				if(q.getCansh()!=null && sqr.getCansh()!=null) {
					if (q.getCansh()==sqr.getCansh()) punti += correct;
					else punti += wrong;
					}
			}
		return punti;
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
