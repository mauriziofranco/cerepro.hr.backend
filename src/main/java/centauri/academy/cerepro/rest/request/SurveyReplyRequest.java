/**
 * 
 */
package centauri.academy.cerepro.rest.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;


/**
 * @author Marco Fulgosi
 *
 */
public class SurveyReplyRequest {

	/* ATTRIBUTI */
	@NotNull(message = "error.surveyId.empty")
	private long surveyId;
	
	@NotNull(message = "error.userId.empty")
	private long userId;
	
	@NotNull(message = "error.starttime.empty")
	private LocalDateTime starttime;
	
	@NotNull(message = "error.endtime.empty")
	private LocalDateTime endtime;
	
	//@NotNull(message = "error.answers.empty")
	private List<SingleQuestionReplyRequest> answers;
	
	private List<InterviewReplyRequest> interviewAnswers;
	
	/**
	 * @return the surveyId
	 */
	public long getSurveyId() {
		return surveyId;
	}
	/**
	 * @param surveyId the surveyId to set
	 */
	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the starttime
	 */
	public LocalDateTime getStarttime() {
		return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(LocalDateTime starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	public LocalDateTime getEndtime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(LocalDateTime endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return the answers
	 */
	public List<SingleQuestionReplyRequest> getAnswers() {
		return answers;
	}
	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<SingleQuestionReplyRequest> answers) {
		this.answers = answers;
	}
	/**
	 * @return the interviewAnswers
	 */
	public List<InterviewReplyRequest> getInterviewAnswers() {
		return interviewAnswers;
	}
	/**
	 * @param interviewAnswers the interviewAnswers to set
	 */
	public void setInterviewAnswers(List<InterviewReplyRequest> interviewAnswers) {
		this.interviewAnswers = interviewAnswers;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SurveyReplyRequest [surveyId=" + surveyId + ", userId=" + userId + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", answers=" + answers + ", interviewAnswers=" + interviewAnswers + "]";
	}

	
	
}




/*
	POST http://localhost:8080/api/v1/surveyreplyrequest/
{
	"surveyId":1,
	"userId":1,
	"starttime": "2018-12-04T13:45:00",
	"endtime": "2018-12-04T13:45:00",
	"answers":
	[
		{
			"questionId":1,
			"cansa":true,
			"cansb":true,
			"cansc":true,
			"cansd":true,
			"canse":true,
			"cansf":true,
			"cansg":true,
			"cansh":true
		},
		{
			"questionId":2,
			"cansa":true,
			"cansb":true,
			"cansc":true,
			"cansd":true,
			"canse":true,
			"cansf":true,
			"cansg":true,
			"cansh":true
		}
	]
}
*/
