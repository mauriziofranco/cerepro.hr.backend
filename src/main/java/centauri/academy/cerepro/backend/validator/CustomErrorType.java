package centauri.academy.cerepro.backend.validator;


import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;


public class CustomErrorType extends  CeReProAbstractEntity {
	private String errorMessage;

	public CustomErrorType(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
