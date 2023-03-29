/**
 * 
 */
package centauri.academy.cerepro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import centauri.academy.cerepro.rest.response.ApplicationInfoResponseModel;

/**
 * 
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@Service 
public class ApplicationInfoService {
	

//	@Value("${project.artifactId}")
//	private String backendApplicationReleaseName ;
	
	@Value("${project.version}")
	private String backendApplicationVersionRelease ;
	@Value("${persistence.version}")
	private String persistenceVersionRelease ;
	
	
	public ApplicationInfoResponseModel getApplicationInfo () {
		
		return new ApplicationInfoResponseModel(backendApplicationVersionRelease, persistenceVersionRelease) ;
		
	}
	
}
