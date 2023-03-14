package centauri.academy.cerepro.dto;

import centauri.academy.cerepro.persistence.entity.custom.QuestionCustom;

public class QuestionAndReply extends QuestionCustom {

	private long questionId;
	private Boolean userCansa;
	private Boolean userCansb;
	private Boolean userCansc;
	private Boolean userCansd;
	private Boolean userCanse;
	private Boolean userCansf;
	private Boolean userCansg;
	private Boolean userCansh;

	public QuestionAndReply() {
		super();
	}

	public QuestionAndReply(long id, String label, String description,
			String ansa, String ansb, String ansc, String ansd, String anse,
			String ansf, String ansg, String ansh, Boolean cansa, Boolean cansb,
			Boolean cansc, Boolean cansd, Boolean canse, Boolean cansf,
			Boolean cansg, Boolean cansh, String fullAnswer, Long position, long questionId, Boolean userCansa, Boolean userCansb,
			Boolean userCansc, Boolean userCansd, Boolean userCanse,
			Boolean userCansf, Boolean userCansg, Boolean userCansh) {
		super(id, label, description, ansa, ansb, ansc, ansd, anse, ansf, ansg,
				ansh, cansa, cansb, cansc, cansd, canse, cansf, cansg, cansh,
				fullAnswer, position);
		this.questionId = questionId;
		this.userCansa = userCansa;
		this.userCansb = userCansb;
		this.userCansc = userCansc;
		this.userCansd = userCansd;
		this.userCanse = userCanse;
		this.userCansf = userCansf;
		this.userCansg = userCansg;
		this.userCansh = userCansh;
	}

	/**
	 * @return the questionId
	 */
	public long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public QuestionAndReply(Boolean userCansa, Boolean userCansb,
			Boolean userCansc, Boolean userCansd, Boolean userCanse,
			Boolean userCansf, Boolean userCansg, Boolean userCansh) {

		this.userCansa = userCansa;
		this.userCansb = userCansb;
		this.userCansc = userCansc;
		this.userCansd = userCansd;
		this.userCanse = userCanse;
		this.userCansf = userCansf;
		this.userCansg = userCansg;
		this.userCansh = userCansh;
	}

	/**
	 * @return the userCansa
	 */
	public Boolean getUserCansa() {
		return userCansa;
	}

	/**
	 * @param userCansa the userCansa to set
	 */
	public void setUserCansa(Boolean userCansa) {
		this.userCansa = userCansa;
	}

	/**
	 * @return the userCansb
	 */
	public Boolean getUserCansb() {
		return userCansb;
	}

	/**
	 * @param userCansb the userCansb to set
	 */
	public void setUserCansb(Boolean userCansb) {
		this.userCansb = userCansb;
	}

	/**
	 * @return the userCansc
	 */
	public Boolean getUserCansc() {
		return userCansc;
	}

	/**
	 * @param userCansc the userCansc to set
	 */
	public void setUserCansc(Boolean userCansc) {
		this.userCansc = userCansc;
	}

	/**
	 * @return the userCansd
	 */
	public Boolean getUserCansd() {
		return userCansd;
	}

	/**
	 * @param userCansd the userCansd to set
	 */
	public void setUserCansd(Boolean userCansd) {
		this.userCansd = userCansd;
	}

	/**
	 * @return the userCanse
	 */
	public Boolean getUserCanse() {
		return userCanse;
	}

	/**
	 * @param userCanse the userCanse to set
	 */
	public void setUserCanse(Boolean userCanse) {
		this.userCanse = userCanse;
	}

	/**
	 * @return the userCansf
	 */
	public Boolean getUserCansf() {
		return userCansf;
	}

	/**
	 * @param userCansf the userCansf to set
	 */
	public void setUserCansf(Boolean userCansf) {
		this.userCansf = userCansf;
	}

	/**
	 * @return the userCansg
	 */
	public Boolean getUserCansg() {
		return userCansg;
	}

	/**
	 * @param userCansg the userCansg to set
	 */
	public void setUserCansg(Boolean userCansg) {
		this.userCansg = userCansg;
	}

	/**
	 * @return the userCansh
	 */
	public Boolean getUserCansh() {
		return userCansh;
	}

	/**
	 * @param userCansh the userCansh to set
	 */
	public void setUserCansh(Boolean userCansh) {
		this.userCansh = userCansh;
	}
	

}
