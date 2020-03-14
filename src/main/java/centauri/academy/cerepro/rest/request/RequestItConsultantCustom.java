package centauri.academy.cerepro.rest.request;

import java.util.Date;

/**
 * @author m.franco
 *
 */
public class RequestItConsultantCustom extends RequestCustom {

	
	public RequestItConsultantCustom() {

	}

	public RequestItConsultantCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath) {
		super(id, userId, domicileCity, studyQualification, graduate, highGraduate,
				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, imgpath);
	}

}
