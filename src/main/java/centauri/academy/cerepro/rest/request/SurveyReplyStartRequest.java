/**
 * 
 */
package centauri.academy.cerepro.rest.request;

import jakarta.validation.constraints.NotNull;

/**
 * @author Dario
 * @author maurizio.franco@ymail.com
 *
 */
public class SurveyReplyStartRequest {

	@NotNull(message = "error.surveyId.empty")
	private long surveyId;
	
	@NotNull(message = "survey.reply.error.candidateId.empty")
	private long candidateId;
	
	@NotNull(message = "error.userTokenId.empty")
	private long userTokenId;

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
	 * @return the candidateId
	 */
	public long getCandidateId() {
		return candidateId;
	}

	/**
	 * @param candidateId the candidateId to set
	 */
	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	/**
	 * @return the userTokenId
	 */
	public long getUserTokenId() {
		return userTokenId;
	}

	/**
	 * @param userTokenId the userTokenId to set
	 */
	public void setUserTokenId(long userTokenId) {
		this.userTokenId = userTokenId;
	}

	@Override
	public String toString() {
		return "SurveyReplyStartRequest [surveyId=" + surveyId + ", candidateId=" + candidateId + ", userTokenId="
				+ userTokenId + "]";
	}
	
	
}



