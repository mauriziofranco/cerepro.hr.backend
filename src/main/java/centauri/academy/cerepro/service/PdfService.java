package centauri.academy.cerepro.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import aj.org.objectweb.asm.Type;
import centauri.academy.cerepro.backend.QuestionController;
import centauri.academy.cerepro.dto.QuestionAndReply;
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.custom.QuestionCustom;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;
import centauri.academy.cerepro.rest.response.ResponseQuestion;

/**
 * 
 * @author git-DaimCod
 *
 */
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
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SurveyReplyService surveyReplyService;

	public boolean generatePdf(Long surveyReplyId) {
		
		logger.debug("################### SERVICE METHOD CALLED ###################");
		
		Optional<SurveyReply> surveyReply = surveyReplyRepository.findById(surveyReplyId);
		Optional<Survey> survey = surveyRepository.findById(surveyReply.get().getSurveyId());
		Optional<Candidate> candidate = candidateService.getById(surveyReply
				.get().getCandidateId());
		List<QuestionCustom> questionCustomList = questionService.getAllQuestionCustomListFromSurveyId(surveyReply.get().getSurveyId());
		List<QuestionAndReply> questionReplyList = this.createQuestionReplyList(questionCustomList, surveyReply.get().getAnswers());
		
		logger.debug("################### SURVEY FOUND WITH ID: " + surveyReply
				.get().getId());
		
		if (!surveyReply.isPresent()) {
			return false;
		}
		
		Document document = new Document();
		String path = env.getProperty("app.folder.candidate.survey.pdf");
		String name = candidate.get().getFirstname() + "-" + candidate.get().getLastname()+"-" 
				+ surveyReply.get().getStarttime().getMonthValue() + "-" 
				+ surveyReply.get().getStarttime().getDayOfMonth() + "-" 
				+ surveyReply.get().getId();
		String aa = path.concat(File.separator).concat(name) + ".pdf";
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					aa
//					"C:\\Users\\Academy 7\\Desktop\\yourAns.pdf"
//					"/home/maurizio/Desktop/iTextHelloWorld.pdf"
					)
					);
			logger.debug("################### FILE CREATED #############");
			
			document.open();
			
			List<String> listaStr = convertInfoToString(candidate.get(), surveyReply
					.get(), questionCustomList, survey.get(), questionReplyList);
			
			for (String a : listaStr) {
				Paragraph para = new Paragraph(a);
				try {
					document.add(para);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
			
			document.close();
			
			surveyReplyService.updatePdfName(name, surveyReply.get().getId());
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return false;	
	}

	private List<QuestionAndReply> createQuestionReplyList(
			List<QuestionCustom> questionCustomList, String answers) {
		
		List<QuestionAndReply> lista = new ArrayList<QuestionAndReply>();
		System.out.println("#### DIMENSIONE QUESTION CUSTOM LIST : " + questionCustomList.size() + " ######");
		
//		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson(); 
		QuestionAndReply[] qarArray = gson.fromJson(answers, QuestionAndReply[].class); 
		int i = 0;
		try {
//			List<QuestionAndReply> qaaq = mapper.readValue(answers, List<QuestionAndReply.class>);
			for(QuestionCustom es : questionCustomList) {
				for(; i < questionCustomList.size()-1; i++) {
				
					QuestionAndReply qar = new QuestionAndReply(es.getId(), es.getLabel(), es.getDescription(), 
							es.getAnsa(), es.getAnsb(), es.getAnsc(), es.getAnsd(), es.getAnse(), es.getAnsf(), 
							es.getAnsg(), es.getAnsh(), es.getCansa(), es.getCansb(), es.getCansc(), es.getCansd(), 
							es.getCanse(), es.getCansf(), es.getCansg(), es.getCansh() , es.getFullAnswer(), es.getPosition(),
							qarArray[i].getQuestionId(), qarArray[i].getUserCansa(), qarArray[i].getUserCansb(), qarArray[i].getUserCansc(), qarArray[i].getUserCansd(), qarArray[i].getUserCanse(),
							qarArray[i].getUserCansf(), qarArray[i].getUserCansg(), qarArray[i].getUserCansh());
					lista.add(qar);
				}
			}
//			System.out.println("####### POSIZIONE: " + i);
//			for(QuestionCustom es : questionCustomList) {
//				
//				System.out.println(es.toString());
//				System.out.println("\n\n");
//				System.out.println("####### POSIZIONE: " + i);
//				i++;
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lista;
	}

	private List<String> convertInfoToString(Candidate candidate,
			SurveyReply surveyReply,
			List<QuestionCustom> questionCustomList,
			Survey survey, List<QuestionAndReply> questionReplyList) {
		
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
		int i = 0;
		for (QuestionCustom rq : questionReplyList) {
				System.out.println("####################### CREAZIONE STRINGA DENTRO FOR ###########");
				System.out.println("####################### dimensione questionCustomList: " + questionCustomList.size());
				
				s = "";
				s += "ID: " + rq.getId() + "\n";
				s += "Description: " + rq.getLabel() + "\n";
				s += "Description: " + rq.getDescription() + "\n";
				s += "Position: " + rq.getPosition() + "\n";
				s += "Answer A: " + rq.getAnsa() + " --- correct Answer:" + rq.getCansa() + " ------ User answer: " + questionReplyList.get(i).getCansa()+ "\n";
				s += "Answer B: " + rq.getAnsb() + " --- correct Answer:" + rq.getCansb() + " ------ User answer: " + questionReplyList.get(i).getCansb() + "\n";
				s += "Answer C: " + rq.getAnsc() + " --- correct Answer:" + rq.getCansc() + " ------ User answer: " + questionReplyList.get(i).getCansc() + "\n";
				s += "Answer D: " + rq.getAnsd() + " --- correct Answer:" + rq.getCansd() + " ------ User answer: " + questionReplyList.get(i).getCansd() + "\n";
				s += "Answer E: " + rq.getAnse() + " --- correct Answer:" + rq.getCanse() + " ------ User answer: " + questionReplyList.get(i).getCanse() + "\n";
				s += "Answer F: " + rq.getAnsf() + " --- correct Answer:" + rq.getCansf() + " ------ User answer: " + questionReplyList.get(i).getCansf() + "\n";
				s += "Answer G: " + rq.getAnsg() + " --- correct Answer:" + rq.getCansg() + " ------ User answer: " + questionReplyList.get(i).getCansg() + "\n";
				s += "Answer H: " + rq.getAnsh() + " --- correct Answer:" + rq.getCansh() + " ------ User answer: " + questionReplyList.get(i).getCansh() + "\n";
				s += "FullAnswer: " + rq.getFullAnswer() + "\n\n\n\n";
				System.out.println("####### String creata: " + s);
				lista.add(s);
				i++;
			
		}
		
		s += "Point: " + surveyReply.getPoints() + "\n";
//		s += "Answers:" + surveyReply.getAnswers() + "\n\n\n\n\n";
		lista.add(s);

		return lista;
	}
}
