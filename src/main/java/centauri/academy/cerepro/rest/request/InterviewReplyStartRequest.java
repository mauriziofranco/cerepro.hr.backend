package centauri.academy.cerepro.rest.request;

import jakarta.validation.constraints.NotNull;

/**
 * @author Fabio
 *
 */
public class InterviewReplyStartRequest {
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InterviewReplyStartRequest [surveyId=" + surveyId + ", userId=" + userId + ", userTokenId="
				+ userTokenId + "]";
	}
	
	
}
