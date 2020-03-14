/**
 * 
 */
package centauri.academy.cerepro.rest.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author Dario
 *
 */
public class SurveyReplyStartRequest {

	/* ATTRIBUTI */
	@NotNull(message = "error.surveyId.empty")
	private long surveyId;
	
	@NotNull(message = "error.userId.empty")
	private long userId;
	
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







	public String toString() {
		/* Method to transform my list of SQL objects into a string */
		StringBuilder sb = new StringBuilder();
		sb.append(" - surveyId: ").append(surveyId);
		sb.append(" - userId: ").append(userId);
	
		
		return sb.toString();
	}
	
}



