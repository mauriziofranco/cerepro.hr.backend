package centauri.academy.cerepro.rest.request.candidate;

import java.util.Arrays;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

/**
 * @author m.franco
 * @author Adela Batalan
 *
 */
public class RequestCustom {

	@NotBlank(message = "Name may not be Blank")
	@Length(max = 50, min =2 )
	protected String firstname;
	
	@NotBlank(message = "Lastname may not be Blank")
	@Length(max = 50, min =2 )
	protected String lastname;
	
	@NotBlank(message = "Email may not be Blank")
	@Length(max = 100, min =5 )
	protected String email;
	
	@Min(1)
	@Max(10000000)
	protected Long id;
	
	@Min(1)
	@Max(10000000)
	protected Long userId;
	
	@NotBlank(message = "City may not be Blank")
	@Length(max = 100, min =5)
	protected String domicileCity;
	
//	protected String domicileStreetName;
//	protected String domicileHouseNumber;
	
	@Length(min=5, max = 300)
	protected String studyQualification;
	
	protected Boolean graduate;
	
	protected Boolean highGraduate;
	
	protected Boolean stillHighStudy;
	
	@NotBlank(message = "Mobile may not be Blank")
	@Length(max = 20, min =5)
	protected String mobile;
	
	@Length(min = 5 ,max = 100)
	protected String cvExternalPath;
	
	@Past
	protected Date dateOfBirth;
	
	@Length(min =1, max = 255)
	protected String imgpath;
	
	@Length(min =1,  max = 100)
	protected String courseCode;
	
	@Length(min =50, max = 2000)
	protected String note;
	
	@NotNull
	protected Long insertedBy ;
	
	
	private MultipartFile[] files;
	
	public RequestCustom() {

	}

	public RequestCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath, String courseCode, String note, long insertedBy) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.id = id;
		this.userId = userId;
		this.domicileCity = domicileCity;
//		this.domicileStreetName = domicileStreetName;
//		this.domicileHouseNumber = domicileHouseNumber;
		this.studyQualification = studyQualification;
		this.graduate = graduate;
		this.highGraduate = highGraduate;
		this.stillHighStudy = stillHighStudy;
		this.mobile = mobile;
		this.cvExternalPath = cvExternalPath;
		this.dateOfBirth = dateOfBirth;
		this.imgpath = imgpath;
		this.courseCode = courseCode;
		this.note = note;
		this.insertedBy=insertedBy;
	}
	
	public RequestCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath, long insertedBy) {
		this(id, userId, domicileCity, studyQualification, graduate, highGraduate,
				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, imgpath, null, null, insertedBy);
	}
//	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the domicileCity
	 */
	public String getDomicileCity() {
		return domicileCity;
	}

	/**
	 * @param domicileCity the domicileCity to set
	 */
	public void setDomicileCity(String domicileCity) {
		this.domicileCity = domicileCity;
	}

	
	/**
	 * @return the studyQualification
	 */
	public String getStudyQualification() {
		return studyQualification;
	}

	/**
	 * @param studyQualification the studyQualification to set
	 */
	public void setStudyQualification(String studyQualification) {
		this.studyQualification = studyQualification;
	}

	/**
	 * @return the graduate
	 */
	public Boolean getGraduate() {
		return graduate;
	}

	/**
	 * @param graduate the graduate to set
	 */
	public void setGraduate(Boolean graduate) {
		this.graduate = graduate;
	}

	/**
	 * @return the highGraduate
	 */
	public Boolean getHighGraduate() {
		return highGraduate;
	}

	/**
	 * @param highGraduate the highGraduate to set
	 */
	public void setHighGraduate(Boolean highGraduate) {
		this.highGraduate = highGraduate;
	}

	/**
	 * @return the stillHighStudy
	 */
	public Boolean getStillHighStudy() {
		return stillHighStudy;
	}

	/**
	 * @param stillHighStudy the stillHighStudy to set
	 */
	public void setStillHighStudy(Boolean stillHighStudy) {
		this.stillHighStudy = stillHighStudy;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the cvExternalPath
	 */
	public String getCvExternalPath() {
		return cvExternalPath;
	}

	/**
	 * @param cvExternalPath the cvExternalPath to set
	 */
	public void setCvExternalPath(String cvExternalPath) {
		this.cvExternalPath = cvExternalPath;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the imgpath
	 */
	public String getImgpath() {
		return imgpath;
	}

	/**
	 * @param imgpath the imgpath to set
	 */
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	/**
	 * @return the courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * @param courseCode the courseCode to set
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the insertedBy
	 */
	public Long getInsertedBy() {
		return insertedBy;
	}

	/**
	 * @param insertedBy the insertedBy to set
	 */
	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
	}

	@Override
	public String toString() {
		return "RequestCustom [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", id=" + id
				+ ", userId=" + userId + ", domicileCity=" + domicileCity + ", studyQualification=" + studyQualification
				+ ", graduate=" + graduate + ", highGraduate=" + highGraduate + ", stillHighStudy=" + stillHighStudy
				+ ", mobile=" + mobile + ", cvExternalPath=" + cvExternalPath + ", dateOfBirth=" + dateOfBirth
				+ ", imgpath=" + imgpath + ", courseCode=" + courseCode + ", note=" + note + ", insertedBy="
				+ insertedBy + ", files=" + Arrays.toString(files) + "]";
	}

}
