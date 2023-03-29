/**
 * 
 */
package centauri.academy.cerepro.rest.response;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author maurizio
 *
 */
@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@ToString
public class ApplicationInfoResponseModel implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private String backendVersion ;
	private String persistenceVersion ;

	/**
	 * @param backendVersion
	 */
	public ApplicationInfoResponseModel(String backendVersion, String persistenceVersion) {
		super();
		this.backendVersion = backendVersion;
		this.persistenceVersion = persistenceVersion;
	}
	
	

}