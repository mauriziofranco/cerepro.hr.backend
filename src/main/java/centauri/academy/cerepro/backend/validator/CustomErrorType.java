package centauri.academy.cerepro.backend.validator;


import centauri.academy.cerepro.persistence.entity.ItConsultantCustom;


public class CustomErrorType extends  ItConsultantCustom {
	private String errorMessage;

	public CustomErrorType(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
