/**
 * 
 */
package centauri.academy.cerepro.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.rest.response.ApplicationInfoResponseModel;
import centauri.academy.cerepro.service.ApplicationInfoService;

/**
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@RestController
@RequestMapping("/api/v1/application/info")
public class ApplicationVersionController {

	public static final Logger logger = LoggerFactory.getLogger(ApplicationVersionController.class);

//	@Value("${project.artifactId}")
//	private String backendApplicationReleaseName ;
//	
//	@Value("${project.version}")
//	private String backendApplicationVersionRelease ;

	@Autowired
	private ApplicationInfoService applicationInfoService ;
	/**
	 * get application version info
	 */
//	@GetMapping("/")
//	public ResponseEntity<ApplicationInfo> getApplicationVersionInfo() {
////		if (BACKEND_APPLICATION_VERSION_RELEASE.isEmpty()) {
////			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
////		}
//		return new ResponseEntity<>(new ApplicationInfo(), HttpStatus.OK);
//	}
	
	@GetMapping("/")
	public ResponseEntity<ApplicationInfoResponseModel> getApplicationVersionInfo() {
//		if (BACKEND_APPLICATION_VERSION_RELEASE.isEmpty()) {
//			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//		}
//		this.backendApplicationReleaseName+"_"+this.backendApplicationVersionRelease
		return new ResponseEntity<>(applicationInfoService.getApplicationInfo(), HttpStatus.OK);
	}
	
	

}