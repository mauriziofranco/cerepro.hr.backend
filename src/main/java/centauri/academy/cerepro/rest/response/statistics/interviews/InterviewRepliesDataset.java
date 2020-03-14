/**
 * 
 */
package centauri.academy.cerepro.rest.response.statistics.interviews;

/**
 * @author luiza
 *
 */
public class InterviewRepliesDataset {

	private Integer cansa ;
	private Integer cansb ;


	
	/**
	 * 
	 */
	public InterviewRepliesDataset() {
		cansa=3;
		cansb=7;

		
	}
	
	public InterviewRepliesDataset(Integer cansa, Integer cansb) {
		this.cansa=cansa;
		this.cansb=cansb;

		
	}

	public Integer getCansa() {
		return cansa;
	}

	public void setCansa(Integer cansa) {
		this.cansa = cansa;
	}

	public Integer getCansb() {
		return cansb;
	}

	public void setCansb(Integer cansb) {
		this.cansb = cansb;
	}
	
	

}
