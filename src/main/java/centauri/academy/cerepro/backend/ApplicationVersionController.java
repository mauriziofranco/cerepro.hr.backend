/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author maurizio.franco@ymail.com
 *
 */
@RestController
@RequestMapping("/api/v1/application/info")
public class ApplicationVersionController {

	public static final Logger logger = LoggerFactory.getLogger(ApplicationVersionController.class);

	

	/**
	 * get application version info
	 */
	@GetMapping("/")
	public ResponseEntity<ApplicationInfo> getApplicationVersionInfo() {
//		if (BACKEND_APPLICATION_VERSION_RELEASE.isEmpty()) {
//			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//		}
		return new ResponseEntity<>(new ApplicationInfo(), HttpStatus.OK);
	}	
	
	class ApplicationInfo implements Serializable {
		
		@Value("${project.artifactId}")
		private String backendApplicationReleaseName;
		
		@Value("${project.version}")
		private String backendApplicationVersionRelease;

		/**
		 * @return the backendApplicationReleaseName
		 */
		public String getBackendApplicationReleaseName() {
			return backendApplicationReleaseName;
		}

		/**
		 * @param backendApplicationReleaseName the backendApplicationReleaseName to set
		 */
		public void setBackendApplicationReleaseName(String backendApplicationReleaseName) {
			this.backendApplicationReleaseName = backendApplicationReleaseName;
		}

		/**
		 * @return the backendApplicationVersionRelease
		 */
		public String getBackendApplicationVersionRelease() {
			return backendApplicationVersionRelease;
		}

		/**
		 * @param backendApplicationVersionRelease the backendApplicationVersionRelease to set
		 */
		public void setBackendApplicationVersionRelease(String backendApplicationVersionRelease) {
			this.backendApplicationVersionRelease = backendApplicationVersionRelease;
		}
		
		
		
	}

}