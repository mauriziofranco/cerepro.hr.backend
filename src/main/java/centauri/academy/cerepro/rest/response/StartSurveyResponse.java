package centauri.academy.cerepro.rest.response;

import java.util.List;

/**
 * Questa classe mi serve per inviare al frontend tutti i dati che ha bisogno.
 * Infatti contiene la lista delle questione che l'utente deve rispondere
 * L'id dell'utente e il surveyId.
 * @author Marco Fulgosi
 *
 */
public class StartSurveyResponse {
	private List<ResponseQuestion> questions;
	private List<ResponseInterview> interviews;
	private long surveyId;
	private long userId;
	private long userTokenId;
	private long time;
	private String afterSurvey;
	private String invalidToken;
	private String expiredToken;

	//private long surveyReplyId;
	/*
	 * 0 - Ok
	 * 1 - Invalid Token 
	 * 2 - Expired Token
	 */
	private int errorCode;
	
	public StartSurveyResponse() {
		this.errorCode = 0;
	}

	/**
	 * @return the questions
	 */
	public List<ResponseQuestion> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<ResponseQuestion> questions) {
		this.questions = questions;
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

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the afterSurvey
	 */
	public String getAfterSurvey() {
		return afterSurvey;
	}

	/**
	 * @param afterSurvey the afterSurvey to set
	 */
	public void setAfterSurvey(String afterSurvey) {
		this.afterSurvey = afterSurvey;
	}

	/**
	 * @return the invalidToken
	 */
	public String getInvalidToken() {
		return invalidToken;
	}

	/**
	 * @param invalidToken the invalidToken to set
	 */
	public void setInvalidToken(String invalidToken) {
		this.invalidToken = invalidToken;
	}

	/**
	 * @return the expiredToken
	 */
	public String getExpiredToken() {
		return expiredToken;
	}

	/**
	 * @param expiredToken the expiredToken to set
	 */
	public void setExpiredToken(String expiredToken) {
		this.expiredToken = expiredToken;
	}

	/**
	 * @return the interiviews
	 */
	public List<ResponseInterview> getInterviews() {
		return interviews;
	}

	/**
	 * @param interiviews the interiviews to set
	 */
	public void setInterviews(List<ResponseInterview> interviews) {
		this.interviews = interviews;
	}
	
	
	
}
