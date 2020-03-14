/**
 * 
 */
package centauri.academy.cerepro.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marco Fulgosi
 *
 */

@RestController
@RequestMapping("/api/v1/chartcontroller")
public class ChartController {

	
	public static final Logger logger = LoggerFactory.getLogger(ChartController.class);
	@GetMapping("/")
	public void creatingChart() {

	}
}
