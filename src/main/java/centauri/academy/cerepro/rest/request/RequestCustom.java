package centauri.academy.cerepro.rest.request;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.AssertTrue.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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
	
	
	private MultipartFile[] files;
	
	public RequestCustom() {

	}

	public RequestCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath, String courseCode, String note) {
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
	}
	
	public RequestCustom(Long id, Long userId, String domicileCity, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String imgpath) {
		this(id, userId, domicileCity, studyQualification, graduate, highGraduate,
				stillHighStudy, mobile, cvExternalPath, email, firstname, lastname, dateOfBirth, imgpath, null, null);
	}

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
	
}
