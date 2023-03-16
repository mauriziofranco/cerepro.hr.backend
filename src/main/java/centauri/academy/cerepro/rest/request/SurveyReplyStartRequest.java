/**
 * 
 */
package centauri.academy.cerepro.rest.request;

import javax.validation.constraints.NotNull;

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
	
	private String generated_token;

	/**
	 * @return the generated_token
	 */
	public String getGenerated_token() {
		return generated_token;
	}

	/**
	 * @param generated_token the generated_token to set
	 */
	public void setGenerated_token(String generated_token) {
		this.generated_token = generated_token;
	}

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



