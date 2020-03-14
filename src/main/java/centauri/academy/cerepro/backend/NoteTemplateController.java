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

import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.NoteTemplate;
import centauri.academy.cerepro.persistence.entity.Role;
import centauri.academy.cerepro.persistence.repository.NoteTemplateRepository;



/**
 * @author maurizio
 * @author marco morgana
 *
 */

@RestController
@RequestMapping("/api/v1/note")
public class NoteTemplateController {
	public static final Logger logger = LoggerFactory.getLogger(NoteTemplateController.class);
	
	@Autowired
	private NoteTemplateRepository noteTemplateRepository ;

	@GetMapping("/")
	public ResponseEntity<List<NoteTemplate>> getNotes() {
		List<NoteTemplate> notes = noteTemplateRepository.findAll();
		
		if (notes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(notes, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> getNoteById(@PathVariable("id") final Long id){
		Optional<NoteTemplate> optNote = noteTemplateRepository.findById(id);
		
		if (!optNote.isPresent()) {
			return new ResponseEntity<>(
				new CustomErrorType("Note with id " + id + " not found"),
				HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(optNote.get(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> createNote(
			@Valid @RequestBody final NoteTemplate Note) {
		logger.info("Creating Note : {}", Note);
		noteTemplateRepository.save(Note);
		return new ResponseEntity<>(Note, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CeReProAbstractEntity> updateNote(
			@PathVariable("id") final Long id, @RequestBody NoteTemplate Note) {
	
		Optional<NoteTemplate> optNote = noteTemplateRepository.findById(id);
		
		if (!optNote.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to upate. Note with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		
		NoteTemplate currentNote = optNote.get();
	
		currentNote.setTitle(Note.getTitle());
		currentNote.setContent(Note.getContent());
		// save currentUser obejct
		noteTemplateRepository.save(currentNote);
		// return ResponseEntity object
		return new ResponseEntity<>(currentNote, HttpStatus.OK);
	}
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<CeReProAbstractEntity> deleteNote(@PathVariable("id") final Long id) {
		Optional<NoteTemplate> optNote = noteTemplateRepository.findById(id);
		
		if (!optNote.isPresent()) {
			return new ResponseEntity<>(
					new CustomErrorType("Unable to delete. Note with id " + id + " not found."), 
					HttpStatus.NOT_FOUND);
		}
		
		noteTemplateRepository.delete(optNote.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
