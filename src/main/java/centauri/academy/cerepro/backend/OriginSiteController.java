/**
 * 
 */
package centauri.academy.cerepro.backend;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.ItConsultantCustom;
import centauri.academy.cerepro.persistence.entity.OriginSite;
import centauri.academy.cerepro.persistence.repository.originsite.OriginSiteRepository;


@RestController
@RequestMapping("/api/v1/originsitescustom")
public class OriginSiteController {
	public static final Logger logger = LoggerFactory.getLogger(OriginSiteController.class);
	
	@Autowired
	private OriginSiteRepository originSiteRepository ;

	/**
	 * getAllOriginSite method gets all originsites 
	 * 
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/")
	public ResponseEntity<List<OriginSite>> getAllOriginSite() {
		List<OriginSite> originsites = originSiteRepository.findAll();
		if (originsites.isEmpty()) {
			return new ResponseEntity<List<OriginSite>>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<List<OriginSite>>(originsites, HttpStatus.OK);
	}

	/******* PAGEABLE ********/

	@GetMapping("/paginated/{size}/{number}/")
	public ResponseEntity<Page<OriginSite>> getAllPaginatedOriginSite(@PathVariable("size") final int size,
			@PathVariable("number") final int number) {
		Page<OriginSite> pos = originSiteRepository.
				findAll(PageRequest.of(number, size, Sort.Direction.ASC, "id"));
		if (pos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(pos, HttpStatus.OK);
	}

	/******** PAGEABLE *******/

	/**
	 * getOriginSiteById method gets a OriginSite by id
	 * 
	 * @param id of the OriginSite to be selected
	 * @return a new ResponseEntity with the given status code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getOriginSiteById(@PathVariable("id") final Long id) {
		OriginSite originSite = null;
		Optional<OriginSite> optOriginSite = originSiteRepository.findById(id);
		if (!optOriginSite.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType("OriginSite with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		} else
			originSite = optOriginSite.get();
		return new ResponseEntity<>(originSite, HttpStatus.OK);
	}

	/**
	 * createOriginSite method creates a originsite
	 * 
	 * @param originsite to be created
	 * @return a new ResponseEntity with the given status code
	 */
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createOriginSite(
			@Valid @RequestBody final OriginSite originSite) {
		logger.info("Creating originSite : {}", originSite);
		originSiteRepository.save(originSite);
		return new ResponseEntity<>(originSite, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateOriginSite(
			@PathVariable("id") final Long id, @RequestBody OriginSite originSite) {
	
		Optional<OriginSite> optOriginSite = originSiteRepository.findById(id);
		
		if (!optOriginSite.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to update. OriginSite with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		
		OriginSite currentOriginSite = optOriginSite.get();
		currentOriginSite.setDescription(originSite.getDescription());
		currentOriginSite.setImgpath(originSite.getImgpath());
		currentOriginSite.setLabel(originSite.getLabel());
		originSiteRepository.save(currentOriginSite);
		return new ResponseEntity<>(currentOriginSite, HttpStatus.OK);
	}
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteOriginSite(@PathVariable("id") final Long id) {
		Optional<OriginSite> optOriginSite = originSiteRepository.findById(id);
		
		if (!optOriginSite.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. OriginSite with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		
		originSiteRepository.delete(optOriginSite.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
