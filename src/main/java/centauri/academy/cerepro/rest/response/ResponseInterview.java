package centauri.academy.cerepro.rest.response;


public class ResponseInterview extends AbstractSurveyResponse {
	
	private String questionText;
	private String ansi;
	private String ansj;
	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}
	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	/**
	 * @return the ansi
	 */
	public String getAnsi() {
		return ansi;
	}
	/**
	 * @param ansi the ansi to set
	 */
	public void setAnsi(String ansi) {
		this.ansi = ansi;
	}
	/**
	 * @return the ansj
	 */
	public String getAnsj() {
		return ansj;
	}
	/**
	 * @param ansj the ansj to set
	 */
	public void setAnsj(String ansj) {
		this.ansj = ansj;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseInterview [questionText=" + questionText + ", ansi=" + ansi + ", ansj=" + ansj + ", id=" + id
				+ ", ansa=" + ansa + ", ansb=" + ansb + ", ansc=" + ansc + ", ansd=" + ansd + ", anse=" + anse
				+ ", ansf=" + ansf + ", ansg=" + ansg + ", ansh=" + ansh + ", position=" + position + "]";
	}
	
	
	


	


	
	
}
