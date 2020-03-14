package centauri.academy.cerepro.rest.request;

import java.util.Date;

/**
 * @author m.franco
 *
 */
public class RequestCandidateCustom extends RequestCustom {

	
	public RequestCandidateCustom() {

	}

	public RequestCandidateCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath, String courseCode, String note) {
		super(id, userId, domicileCity, studyQualification, graduate, highGraduate,
				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, imgpath, courseCode, note);
	}

	@Override
	public String toString() {
		return "RequestCandidateCustom [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", id=" + id + ", userId=" + userId + ", domicileCity=" + domicileCity + ", studyQualification="
				+ studyQualification + ", graduate=" + graduate + ", highGraduate=" + highGraduate + ", stillHighStudy="
				+ stillHighStudy + ", mobile=" + mobile + ", cvExternalPath=" + cvExternalPath + ", dateOfBirth="
				+ dateOfBirth + ", imgpath=" + imgpath + ", courseCode=" + courseCode + ", note=" + note + "]";
	}


	
	

}
