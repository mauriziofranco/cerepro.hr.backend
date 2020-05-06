package centauri.academy.cerepro.rest.request;

import java.util.Date;

/**
 * @author m.franco
 *
 */
public class RequestCandidateCustom extends RequestCustom {

	
	/**
	 * Need for Spring auto conversion in controller methods input
	 */
	public RequestCandidateCustom() {

	}

	public RequestCandidateCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath, String courseCode, String note, Long insertedBy) {
		super(id, userId, domicileCity, studyQualification, graduate, highGraduate,
				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, imgpath, courseCode, note, insertedBy);
	}
	
	/**
	 *  For test: whenPostNewSimpleCandidateCustom_thenStatus201
	 */
	public RequestCandidateCustom(Long userId, String email, String firstname, String lastname, String courseCode, Long insertedBy) {
		super(null, userId, null, null, null, null, null, null, null, email, firstname, lastname, null, null, courseCode, null, insertedBy);
	}
	
	

}
