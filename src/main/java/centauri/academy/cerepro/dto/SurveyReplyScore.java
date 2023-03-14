/**
 * 
 */
package centauri.academy.cerepro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author maurizio
 *
 */
@Getter @Setter @NoArgsConstructor @ToString
public class SurveyReplyScore {
	private long minRangeScore ;
	private long maxRangeScore ;
	private long score ;
	private short scoreAsPercentage ;
	
}
