package centauri.academy.cerepro.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.proxima.common.mail.MailUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import centauri.academy.cerepro.backend.QuestionController;
import centauri.academy.cerepro.dto.AswerJsonResponse;
import centauri.academy.cerepro.dto.QuestionAndReply;
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.custom.QuestionCustom;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;
import centauri.academy.cerepro.persistence.repository.surveyreply.SurveyReplyRepository;

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
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;

	public boolean generatePdf(Long surveyReplyId) {
		
		logger.debug("################### generatePdf START ###################");
		
		Optional<SurveyReply> surveyReply = surveyReplyRepository.findById(surveyReplyId);
		Optional<Survey> survey = surveyRepository.findById(surveyReply.get().getSurveyId());
		Optional<Candidate> candidate = candidateService.getById(surveyReply
				.get().getCandidateId());
		List<QuestionCustom> questionCustomList = questionService.getAllQuestionCustomListFromSurveyId(surveyReply.get().getSurveyId());
		List<QuestionAndReply> questionReplyList = null;
		
		try {
			questionReplyList = this.createQuestionReplyList(questionCustomList, surveyReply.get().getAnswers());
			//System.out.println("######## QUESTION LIST è null? " + questionCustomList == null);
			if(questionReplyList == null)
				return false;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		
		logger.debug("################### SURVEY FOUND WITH ID: " + surveyReply
				.get().getId());
		
		if (!surveyReply.isPresent()) {
			return false;
		}
		
		
		Document document = new Document();
		String path = env.getProperty("app.folder.candidate.survey.pdf");
		logger.info("generatePdf - DEBUG - app.folder.candidate.survey.pdf: " + path);
		String name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss")) + "-" + surveyReply.get().getId() + ".pdf";
		String aa = path.concat(File.separator).concat(name);
		boolean pdfGenerated = false ;
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
			
			pdfGenerated = surveyReplyService.updatePdfName(name, surveyReply.get().getId());
			surveyReply = surveyReplyRepository.findById(surveyReplyId) ;
			logger.info("generatePdf - DEBUG - New object retrieved={}", surveyReply.get());
			String pdffilename = surveyReply.get().getPdffilename();
			if (pdfGenerated&&pdffilename!=null) {
				
				sendEmailWithPdf(candidate.get().getId(), surveyReply.get());
			} else {
			    logger.error("Nessun PDF trovato da inviare.");
			}
			
//			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return pdfGenerated;	
	}
	
	private void sendEmailWithPdf (long candidateId, SurveyReply surveyReply) {
		try {
			Optional<Candidate> optCandidate = candidateRepository.findById(candidateId);
			long insertedBy = optCandidate.get().getInsertedBy();
			Optional<User> optUser = userRepository.findById(insertedBy);
			String recipient = optUser.get().getEmail();
			
		    String subject = "Nuovo PDF generato da " + optCandidate.get().getFirstname() + optCandidate.get().getLastname();
		    String mess = "Ciao, in allegato il PDF generato al termine del questionario compilato da " + optCandidate.get().getFirstname() + " " + optCandidate.get().getLastname() + ", candidato con ID " + optCandidate.get().getId() + ".";
		    
		    String path = env.getProperty("app.folder.candidate.survey.pdf");
			String name = optCandidate.get().getFirstname() + "-" + optCandidate.get().getLastname() + "-" 
					+ surveyReply.getStarttime().getMonthValue() + "-" 
					+ surveyReply.getStarttime().getDayOfMonth() + "-" 
					+ surveyReply.getId() + ".pdf";
		    String attachmentPath = path.concat(File.separator).concat(name);
		    
		    String pdfFileName = surveyReply.getPdffilename();
		    String attachmentName = pdfFileName;
		    
		    logger.info("sendEmailWithPdf - DEBUG - recipient={} - subject={} - mess={}, attachmentPath={}, attachmentName={}", recipient, subject, mess, attachmentPath, attachmentName);
		    boolean mailSent = MailUtility.sendMailWithAttachment(recipient, subject, mess, attachmentPath, attachmentName);

		    if (mailSent) {
		        logger.info("E-mail inviata con successo.");
		    } else {
		        logger.error("Errore durante l'invio dell'e-mail.");
		    }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private List<QuestionAndReply> createQuestionReplyList(
			List<QuestionCustom> questionCustomList, String answers) throws Exception{
		
		List<QuestionAndReply> lista = new ArrayList<QuestionAndReply>();
		logger.info("createQuestionReplyList - DEBUG - #### DIMENSIONE QUESTION CUSTOM LIST : " + questionCustomList.size() + " ######");
		
//		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson(); 
		logger.info("createQuestionReplyList - DEBUG - answers: {}", answers);
		AswerJsonResponse[] qarArray = gson.fromJson(answers, AswerJsonResponse[].class); 
		logger.info("qarArray.length: " + qarArray.length);
		logger.info("questionCustomList.size(): " + questionCustomList.size());
//		int i = 0;
		if(answers == null || answers.equals(""))
			return null;
		try {
//			List<QuestionAndReply> qaaq = mapper.readValue(answers, List<QuestionAndReply.class>);
			for(QuestionCustom es : questionCustomList) {
				logger.info("createQuestionReplyList - DEBUG - " + es);
				for (int i=0; i<qarArray.length; i++) {
					logger.info("qarArray[i].getQuestionId(): "+qarArray[i].getQuestionId());
					logger.info("es.getId(): " + es.getId());
					if (qarArray[i].getQuestionId()==es.getId()) {
						QuestionAndReply qar = new QuestionAndReply(es.getId(), es.getLabel(), es.getDescription(), 
								es.getAnsa(), es.getAnsb(), es.getAnsc(), es.getAnsd(), es.getAnse(), es.getAnsf(), 
								es.getAnsg(), es.getAnsh(), es.getCansa(), es.getCansb(), es.getCansc(), es.getCansd(), 
								es.getCanse(), es.getCansf(), es.getCansg(), es.getCansh() , es.getFullAnswer(), es.getPosition(),
								qarArray[i].getQuestionId(), qarArray[i].getCansa(), qarArray[i].getCansb(), qarArray[i].getCansc(), qarArray[i].getCansd(), qarArray[i].getCanse(),
								qarArray[i].getCansf(), qarArray[i].getCansg(), qarArray[i].getCansh());
						logger.info("createQuestionReplyList - DEBUG - qar={}", qar);
						lista.add(qar);
					}
				}
//				for(; i < questionCustomList.size()-1; i++) {
//				
//					QuestionAndReply qar = new QuestionAndReply(es.getId(), es.getLabel(), es.getDescription(), 
//							es.getAnsa(), es.getAnsb(), es.getAnsc(), es.getAnsd(), es.getAnse(), es.getAnsf(), 
//							es.getAnsg(), es.getAnsh(), es.getCansa(), es.getCansb(), es.getCansc(), es.getCansd(), 
//							es.getCanse(), es.getCansf(), es.getCansg(), es.getCansh() , es.getFullAnswer(), es.getPosition(),
//							qarArray[i].getQuestionId(), qarArray[i].getUserCansa(), qarArray[i].getUserCansb(), qarArray[i].getUserCansc(), qarArray[i].getUserCansd(), qarArray[i].getUserCanse(),
//							qarArray[i].getUserCansf(), qarArray[i].getUserCansg(), qarArray[i].getUserCansh());
//					lista.add(qar);
//				}
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
			return null;
		}
		
		
		return lista;
	}

	private List<String> convertInfoToString(Candidate candidate,
			SurveyReply surveyReply,
			List<QuestionCustom> questionCustomList,
			Survey survey, List<QuestionAndReply> questionReplyList) {
		
		List<String> lista = new ArrayList<String>();

		String s = "Candidate complete name: ";
//		s += candidate.getClass().getSimpleName().toUpperCase() + "\n";
//		s += "ID: " + candidate.getId() + "\n";
//		s += "Course code: " + candidate.getCourseCode() + "\n";
//		s += "Email: " + candidate.getEmail() + "\n";
//		s += "Firstname: " + candidate.getFirstname() + "\n";
//		s += "Lastname: " + candidate.getLastname() + "\n";		
		s += candidate.getFirstname() + " " + candidate.getLastname() + "\n";
		s += "Candidate email: " + candidate.getEmail() + "\n";
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
		s += "Exam name: " + survey.getLabel() + "(" + questionCustomList.size() + " questions)\n";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		s += "Exam execution day: " + surveyReply.getStarttime().format(formatter) + "\n";
		s += "Survey max execution time: " + survey.getTime() + " minuti\n";
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		s += "Start time: " + surveyReply.getStarttime().format(formatter) + " - End time: " + surveyReply.getEndtime().format(formatter) + "\n";
		s += "\n\n\n";
		lista.add(s);
		int i = 0;
		for (QuestionCustom rq : questionReplyList) {
				logger.info("####################### CREAZIONE STRINGA DENTRO FOR ###########");
				logger.info("####################### dimensione questionCustomList: " + questionCustomList.size());
				
				s = "";
//				s += "ID: " + rq.getId() + "\n";
				s += "Question number: " + rq.getPosition() + " - " + rq.getLabel() + "\n";
				s += rq.getDescription() + "\n";
//				s += "Position: " + rq.getPosition() + "\n";
				if (rq.getAnsa()!=null) {
					s += "Answer A: " + rq.getAnsa() + "(" + rq.getCansa() + "): " + questionReplyList.get(i).getUserCansa()+ "\n";
				}
				if (rq.getAnsb()!=null) {
				    s += "Answer B: " + rq.getAnsb() + "(" + rq.getCansb() + "): " + questionReplyList.get(i).getUserCansb() + "\n";
				}
				if (rq.getAnsc()!=null) {
					s += "Answer C: " + rq.getAnsc() + "(" + rq.getCansc() + "): " + questionReplyList.get(i).getUserCansc() + "\n";
				}
				if (rq.getAnsd()!=null) {
					s += "Answer D: " + rq.getAnsd() + "(" + rq.getCansd() + "): " + questionReplyList.get(i).getUserCansd() + "\n";
				}
				if (rq.getAnse()!=null) {
					s += "Answer E: " + rq.getAnse() + "(" + rq.getCanse() + "): " + questionReplyList.get(i).getUserCanse() + "\n";
				}
				if (rq.getAnsf()!=null) {
				    s += "Answer F: " + rq.getAnsf() + "(" + rq.getCansf() + "): " + questionReplyList.get(i).getUserCansf() + "\n";
				}
				if (rq.getAnsg()!=null) {
				    s += "Answer G: " + rq.getAnsg() + "(" + rq.getCansg() + "): " + questionReplyList.get(i).getUserCansg() + "\n";
				}
				if (rq.getAnsh()!=null) {
					s += "Answer H: " + rq.getAnsh() + "(" + rq.getCansh() + "): " + questionReplyList.get(i).getUserCansh() + "\n";
				}
				s += "\n";
				s += "Full answer: " + rq.getFullAnswer() + "\n\n\n\n";
				logger.info("####### String creata: " + s);
				lista.add(s);
				i++;
			
		}
		
		s = "Exam final result: " + surveyReply.getPoints() + " points\n";
//		s += "Answers:" + surveyReply.getAnswers() + "\n\n\n\n\n";
		lista.add(s);

		return lista;
	}
}
