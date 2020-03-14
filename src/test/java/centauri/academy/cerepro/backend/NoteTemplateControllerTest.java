package centauri.academy.cerepro.backend;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import centauri.academy.cerepro.CeReProBackendApplication;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.NoteTemplate;
import centauri.academy.cerepro.persistence.repository.NoteTemplateRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeReProBackendApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class NoteTemplateControllerTest {

	public static final Logger logger = LoggerFactory.getLogger(NoteTemplateControllerTest.class);

	@Spy
	private NoteTemplateController noteTemplateController;
	
	@Mock
	private NoteTemplateRepository noteTemplateRepository;
	
	@Before
	public void setup() {
		noteTemplateController = new NoteTemplateController();
		ReflectionTestUtils.setField(noteTemplateController, "noteTemplateRepository", noteTemplateRepository);
	}

	@Test
	public void testListAllNotes() {
		List<NoteTemplate> NoteList = new ArrayList<NoteTemplate>();
		NoteList.add(new NoteTemplate());
		when(this.noteTemplateRepository.findAll()).thenReturn(NoteList);
		ResponseEntity<List<NoteTemplate>> responseEntity = this.noteTemplateController.getNotes();
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
	}

	@Test
	public void testGetNoteById() {
//		NoteTemplate testNote = new NoteTemplate (100L, "test", "tester") ;
		NoteTemplate testNote = new NoteTemplate () ;
		testNote.setId(100L);
		testNote.setTitle("test");
		testNote.setContent("tester");
		Optional<NoteTemplate> currOpt = Optional.of(testNote) ;
		when(this.noteTemplateRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.noteTemplateController.getNoteById(100L);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("test", ((NoteTemplate)responseEntity.getBody()).getTitle());
		Assert.assertEquals("tester", ((NoteTemplate)responseEntity.getBody()).getContent());
	}
	
	@Test
	public void testInsertNoteSuccessfully() {
		//NoteTemplate testNote = new NoteTemplate (100L, "test", "tester") ;
		NoteTemplate testNote = new NoteTemplate () ;
		testNote.setId(100L);
		testNote.setTitle("test");
		testNote.setContent("tester");
//		NoteTemplate testNote = new NoteTemplate (100L, "test", "tester") ;
		when(this.noteTemplateRepository.save(testNote)).thenReturn(testNote);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.noteTemplateController.createNote(testNote);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Assert.assertEquals("test", ((NoteTemplate)responseEntity.getBody()).getTitle());
		Assert.assertEquals("tester", ((NoteTemplate)responseEntity.getBody()).getContent());
	}
	
	@Test
	public void testDeleteNoteSuccessfully() {
//		NoteTemplate testNote = new NoteTemplate (100L, "test", "tester") ;
		NoteTemplate testNote = new NoteTemplate () ;
		testNote.setId(100L);
		testNote.setTitle("test");
		testNote.setContent("tester");
		Optional<NoteTemplate> currOpt = Optional.of(testNote) ;
		when(this.noteTemplateRepository.findById(100L)).thenReturn(currOpt);
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.noteTemplateController.deleteNote(100L);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdateNoteSuccessfully() {
//		NoteTemplate testNote = new NoteTemplate (100L, "test", "tester") ;
		NoteTemplate testNote = new NoteTemplate () ;
		testNote.setId(100L);
		testNote.setTitle("test");
		testNote.setContent("tester");
		Optional<NoteTemplate> currOpt = Optional.of(testNote) ;
		when(this.noteTemplateRepository.findById(100L)).thenReturn(currOpt);
		testNote.setContent("testerUPDATED");
		ResponseEntity<CeReProAbstractEntity> responseEntity = this.noteTemplateController.updateNote(100L, testNote);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals("testerUPDATED", ((NoteTemplate)responseEntity.getBody()).getContent());
	}
	
	@After
	public void teardown() {
		noteTemplateController = null;
	}

}
