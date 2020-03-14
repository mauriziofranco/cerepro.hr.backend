/**
 * 
 */
package centauri.academy.cerepro.rest.response.statistics.interviews;

import java.util.ArrayList;

/**
 * @author luiza
 *
 */
public class InterviewPieChartDataset {

	private Long interview_id ;
	private InterviewRepliesDataset interviewReplies ; 
	
	/**
	 * 
	 */
	public InterviewPieChartDataset() {
		interview_id = 1l;
		
		int valuea = (int)(Math.random()*100);
		int valueb = 100 - valuea ;
		interviewReplies = new InterviewRepliesDataset(valuea, valueb);
	}

	public Long getInterview_id() {
		return interview_id;
	}

	public void setInterview_id(Long interview_id) {
		this.interview_id = interview_id;
	}

	/**
	 * @return the interviewReplies
	 */
	public InterviewRepliesDataset getInterviewReplies() {
		return interviewReplies;
	}

	/**
	 * @param interviewReplies the interviewReplies to set
	 */
	public void setInterviewReplies(InterviewRepliesDataset interviewReplies) {
		this.interviewReplies = interviewReplies;
	}

	
	

}
