/**
 * 
 */
package centauri.academy.cerepro.rest.response.statistics.course_code;

/**
 * @author maurizio
 *
 */
public class CourseCodeStatisticsModel {
	
	private String courseCode ;
	private Integer candidatesNumber ;
	
	public CourseCodeStatisticsModel(String courseCode, Integer candidatesNumber) {
		super();
		this.courseCode = courseCode;
		this.candidatesNumber = candidatesNumber;
	}
	
	public CourseCodeStatisticsModel() {
		super();
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
	 * @return the candidatesNumber
	 */
	public Integer getCandidatesNumber() {
		return candidatesNumber;
	}
	/**
	 * @param candidatesNumber the candidatesNumber to set
	 */
	public void setCandidatesNumber(Integer candidatesNumber) {
		this.candidatesNumber = candidatesNumber;
	}
	
	@Override
	public String toString() {
		return "CourseCodeStatisticsModel [courseCode=" + courseCode + ", candidatesNumber=" + candidatesNumber + "]";
	}
	
	

}
