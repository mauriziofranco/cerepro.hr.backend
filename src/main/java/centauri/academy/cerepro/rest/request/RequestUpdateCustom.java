package centauri.academy.cerepro.rest.request;

import java.util.Arrays;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;

/**
 * @author m.franco
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RequestUpdateCustom extends CeReProAbstractEntity {

	protected Long id;
	protected Long userId;
	protected String domicileCity;
	protected String domicileStreetName;
	protected String domicileHouseNumber;
	protected String studyQualification;
	protected Boolean graduate;
	protected Boolean highGraduate;
	protected Boolean stillHighStudy;
	protected String mobile;
	protected String cvExternalPath;
	protected String email;
	protected String firstname;
	protected String lastname;
	protected String note;
	protected Date dateOfBirth;
	protected String imgpath;
	protected String oldImg;
	protected String oldCV;
	protected Long candidateStatesId;

	public RequestUpdateCustom() {

	}

	public RequestUpdateCustom(Long id, Long userId, String domicileCity, String domicileStreetName,
			String domicileHouseNumber, String studyQualification, Boolean graduate, Boolean highGraduate,
			Boolean stillHighStudy, String mobile, String cvExternalPath, String email, String firstname,
			String lastname, Date dateOfBirth, String note, String imgpath, String oldImg, String oldCV,
			MultipartFile[] files, Long candidateStatesId) {
		super();
		this.id = id;
		this.userId = userId;
		this.domicileCity = domicileCity;
		this.domicileStreetName = domicileStreetName;
		this.domicileHouseNumber = domicileHouseNumber;
		this.studyQualification = studyQualification;
		this.graduate = graduate;
		this.highGraduate = highGraduate;
		this.stillHighStudy = stillHighStudy;
		this.mobile = mobile;
		this.cvExternalPath = cvExternalPath;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.note = note;
		this.imgpath = imgpath;
		this.oldImg = oldImg;
		this.oldCV = oldCV;
		this.files = files;
		this.candidateStatesId = candidateStatesId;
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
	 * @return the domicileStreetName
	 */
	public String getDomicileStreetName() {
		return domicileStreetName;
	}

	/**
	 * @param domicileStreetName the domicileStreetName to set
	 */
	public void setDomicileStreetName(String domicileStreetName) {
		this.domicileStreetName = domicileStreetName;
	}

	/**
	 * @return the domicileHouseNumber
	 */
	public String getDomicileHouseNumber() {
		return domicileHouseNumber;
	}

	/**
	 * @param domicileHouseNumber the domicileHouseNumber to set
	 */
	public void setDomicileHouseNumber(String domicileHouseNumber) {
		this.domicileHouseNumber = domicileHouseNumber;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * @return the oldImg
	 */
	public String getOldImg() {
		return oldImg;
	}

	/**
	 * @param oldImg the oldImg to set
	 */
	public void setOldImg(String oldImg) {
		this.oldImg = oldImg;
	}

	/**
	 * @return the oldCV
	 */
	public String getOldCV() {
		return oldCV;
	}

	/**
	 * @param oldCV the oldCV to set
	 */
	public void setOldCV(String oldCV) {
		this.oldCV = oldCV;
	}

	private MultipartFile[] files;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	/**
	 * @return the candidateStatesId
	 */
	public Long getCandidateStatesId() {
		return candidateStatesId;
	}

	/**
	 * @param candidateStatesId the candidateStatesId to set
	 */
	public void setCandidateStatesId(Long candidateStatesId) {
		this.candidateStatesId = candidateStatesId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestUpdateCustom [id=" + id + ", userId=" + userId + ", domicileCity=" + domicileCity
				+ ", domicileStreetName=" + domicileStreetName + ", domicileHouseNumber=" + domicileHouseNumber
				+ ", studyQualification=" + studyQualification + ", graduate=" + graduate + ", highGraduate="
				+ highGraduate + ", stillHighStudy=" + stillHighStudy + ", mobile=" + mobile + ", cvExternalPath="
				+ cvExternalPath + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", note=" + note + ", dateOfBirth=" + dateOfBirth + ", imgpath=" + imgpath + ", oldImg=" + oldImg
				+ ", oldCV=" + oldCV + ", candidateStatesId=" + candidateStatesId + ", files=" + Arrays.toString(files)
				+ "]";
	}

	

}
