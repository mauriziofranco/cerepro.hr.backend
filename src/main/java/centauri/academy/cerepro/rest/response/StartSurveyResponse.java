package centauri.academy.cerepro.rest.response;

import java.util.List;

/**
 * Questa classe mi serve per inviare al frontend tutti i dati che ha bisogno.
 * Infatti contiene la lista delle questione che l'utente deve rispondere
 * L'id del candidato e il surveyId.
 * @author Marco Fulgosi
 * @author maurizio.franco@ymail.com
 *
 */
public class StartSurveyResponse {
	private List<ResponseQuestion> questions;
	private long surveyId;
	private long candidateId;
	private long candidateTokenId;
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
	 * @return the candidateTokenId
	 */
	public long getCandidateTokenId() {
		return candidateTokenId;
	}

	/**
	 * @param candidateTokenId the candidateTokenId to set
	 */
	public void setCandidateTokenId(long candidateTokenId) {
		this.candidateTokenId = candidateTokenId;
	}

	@Override
	public String toString() {
		return "StartSurveyResponse [questions=" + questions + ", surveyId=" + surveyId + ", candidateId=" + candidateId
				+ ", candidateTokenId=" + candidateTokenId + ", time=" + time + ", afterSurvey=" + afterSurvey
				+ ", invalidToken=" + invalidToken + ", expiredToken=" + expiredToken + ", errorCode=" + errorCode
				+ "]";
	}

}
