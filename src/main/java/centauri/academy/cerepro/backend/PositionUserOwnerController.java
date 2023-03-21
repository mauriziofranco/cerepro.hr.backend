package centauri.academy.cerepro.backend;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.PositionUserOwner;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.service.PositionUserOwnerService;

/**
 * 
 * @author m.peruzza@proximanetwork.it
 *
 */

@RestController
@RequestMapping("/api/v1/positionuserowner")
public class PositionUserOwnerController {

	public static final Logger logger = LoggerFactory.getLogger(PositionUserOwnerController.class);
	
	@Autowired
	private PositionUserOwnerService positionUserOwnerService;
	
	@GetMapping("/{coursepageid}")
	public ResponseEntity<PositionUserOwner> getByCoursePageId(@PathVariable("coursepageid") final Long couserPageId) {
		
		Optional<PositionUserOwner> positionUserOwner = positionUserOwnerService.getByCoursePageId(couserPageId);
		if (!positionUserOwner.isPresent()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else return new ResponseEntity<>(positionUserOwner.get(), HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PositionUserOwner> createPositionUserOwner(@Valid @RequestBody final PositionUserOwner positionUserOwner) {
		
		logger.info("Creating PositionUserOwner : {}", positionUserOwner);
		
		positionUserOwnerService.insert(positionUserOwner);
		return new ResponseEntity<>(positionUserOwner, HttpStatus.CREATED);
		
	}
	
}
