/**
 * 
 */
package centauri.academy.cerepro.rest.response.statistics.interviews;

import java.util.ArrayList;

/**
 * @author luiza
 *
 */
public class SurveyPieChartDataset {

	private Long survey_id ;
	private ArrayList<InterviewPieChartDataset> interviews;
	
	/**
	 * 
	 */
	public SurveyPieChartDataset() {
		survey_id=5l;
		interviews = new ArrayList<InterviewPieChartDataset> ();
		interviews.add(new InterviewPieChartDataset());
		InterviewPieChartDataset aaa = new InterviewPieChartDataset();
		aaa.setInterview_id(2l);
		interviews.add(aaa);
		
	}

	/**
	 * @return the survey_id
	 */
	public Long getSurvey_id() {
		return survey_id;
	}

	/**
	 * @param survey_id the survey_id to set
	 */
	public void setSurvey_id(Long survey_id) {
		this.survey_id = survey_id;
	}

	/**
	 * @return the interviews
	 */
	public ArrayList<InterviewPieChartDataset> getInterviews() {
		return interviews;
	}

	/**
	 * @param interviews the interviews to set
	 */
	public void setInterviews(ArrayList<InterviewPieChartDataset> interviews) {
		this.interviews = interviews;
	}
	
	

}
