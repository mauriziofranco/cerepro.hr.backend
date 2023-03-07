package centauri.academy.cerepro.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import centauri.academy.cerepro.backend.QuestionController;
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.custom.QuestionCustom;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.rest.response.ResponseQuestion;

@Service
public class PdfService {

	public static final Logger logger = LoggerFactory.getLogger(
			QuestionController.class);

	@Autowired
	private SurveyReplyRepository surveyReplyRepository;

	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private SurveyRepository surveyRepository;

	public ResponseEntity<CeReProAbstractEntity> generatePdf(Long id) {
		
		logger.debug("################### SERVICE METHOD CALLED ###################");
		
		Optional<SurveyReply> surveyReply = surveyReplyRepository.findById(id);
		Optional<Survey> survey = surveyRepository.findById(surveyReply.get().getSurveyId());
		Optional<Candidate> candidate = candidateService.getById(surveyReply
				.get().getCandidateId());
		List<QuestionCustom> questionCustomList = questionService.getAllQuestionCustomListFromSurveyId(surveyReply.get().getSurveyId());
		
		logger.debug("################### SURVEY FOUND WITH ID: " + surveyReply
				.get().getId());
		
		if (!surveyReply.isPresent()) {
			return new ResponseEntity<>(new CustomErrorType(
					"Unable to generate PDF. SurveyReply with id " + id
							+ " not found."), HttpStatus.NOT_FOUND);
		}
		
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
//					"C:\\Users\\Academy 7\\Desktop\\iTextHelloWorld.pdf"
					"/home/maurizio/Desktop/iTextHelloWorld.pdf"
					)
					);
			logger.debug("################### FILE CREATED #############");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		document.open();
		List<String> listaStr = convertInfoToString(candidate.get(), surveyReply
				.get(), questionCustomList, survey.get());
		
		for (String a : listaStr) {
			Paragraph para = new Paragraph(a);
			try {
				document.add(para);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		
		document.close();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private List<String> convertInfoToString(Candidate candidate,
			SurveyReply surveyReply,
			List<QuestionCustom> questionCustomList,
			Survey survey) {
		List<String> lista = new ArrayList<String>();

		String s = "";
//		s += candidate.getClass().getSimpleName().toUpperCase() + "\n";
//		s += "ID: " + candidate.getId() + "\n";
//		s += "Course code: " + candidate.getCourseCode() + "\n";
//		s += "Email: " + candidate.getEmail() + "\n";
//		s += "Firstname: " + candidate.getFirstname() + "\n";
//		s += "Lastname: " + candidate.getLastname() + "\n";		
		s += candidate.getFirstname() + " " + candidate.getLastname() + "\n";
		s += candidate.getEmail() + "\n";
//		s += "DateOfBirth: " + candidate.getDateOfBirth() + "\n";
//		s += "Residential city: " + candidate.getDomicileCity() + "\n";
//		s += "Mobile: " + candidate.getMobile() + "\n";
//		s += "StudyQualification: " + candidate.getStudyQualification() + "\n";
//		s += "HrNote: " + candidate.getHrNote() + "\n";
//		s += "Inserted by: " + candidate.getInsertedBy() + "\n";
//		s += "Is graduadated: " + candidate.getGraduate() + "\n";
//		s += "CV path: " + candidate.getCvExternalPath() + "\n";
//		s += "When did he candidated: " + candidate.getCandidacyDateTime()
//				+ "\n";
//		s += "Status code: " + candidate.getCandidateStatusCode() + "\n";
//		s += "Image path: " + candidate.getImgpath() + "\n\n\n\n\n";
		
		lista.add(s);
		

//		s = "";
//		s += surveyReply.getClass().getSimpleName().toUpperCase() + "\n";
//		s += "ID survey: " + surveyReply.getSurveyId() + "\n";
//		s += "Candidate ID:" + surveyReply.getCandidateId() + "\n";
//		s += "Start time: " + surveyReply.getStarttime() + "\n";
//		s += "End time: " + surveyReply.getEndtime() + "\n";
//		s += "Point: " + surveyReply.getPoints() + "\n";
//		s += "Answers:" + surveyReply.getAnswers() + "\n\n\n\n\n";
		
		s = "";
		s += "Survey type: " + survey.getLabel() + "(" + questionCustomList.size() + " questions)\n";
		s += "Execution start time: " + surveyReply.getStarttime() + "\n";
		s += "Execution end time: " + surveyReply.getEndtime() + "\n";
		s += "\n\n\n\n\n";
		lista.add(s);

		for (QuestionCustom rq : questionCustomList) {
			s = "";
			s += "ID: " + rq.getId() + "\n";
			s += "Description: " + rq.getLabel() + "\n";
			s += "Description: " + rq.getDescription() + "\n";
			s += "Position: " + rq.getPosition() + "\n";
			s += "Answer A: " + rq.getAnsa() + " - correct Answer:" + rq.getCansa() + "\n";
			s += "Answer B: " + rq.getAnsb() + " - correct Answer:" + rq.getCansb() + "\n";
			s += "Answer C: " + rq.getAnsc() + " - correct Answer:" + rq.getCansc() + "\n";
			s += "Answer D: " + rq.getAnsd() + " - correct Answer:" + rq.getCansd() + "\n";
			s += "Answer E: " + rq.getAnse() + " - correct Answer:" + rq.getCanse() + "\n";
			s += "Answer F: " + rq.getAnsf() + " - correct Answer:" + rq.getCansf() + "\n";
			s += "Answer G: " + rq.getAnsg() + " - correct Answer:" + rq.getCansg() + "\n";
			s += "Answer H: " + rq.getAnsh() + " - correct Answer:" + rq.getCansh() + "\n";
			s += "FullAnswer: " + rq.getFullAnswer() + "\n\n\n\n";
			lista.add(s);
		}
		
		s += "Point: " + surveyReply.getPoints() + "\n";
		s += "Answers:" + surveyReply.getAnswers() + "\n\n\n\n\n";
		lista.add(s);

		return lista;
	}
}
