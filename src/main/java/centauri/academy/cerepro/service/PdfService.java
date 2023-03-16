package centauri.academy.cerepro.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.proxima.common.mail.MailUtility;
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
import centauri.academy.cerepro.backend.PdfController;
import centauri.academy.cerepro.backend.QuestionController;
import centauri.academy.cerepro.dto.QuestionAndReply;
import centauri.academy.cerepro.persistence.entity.Candidate;
import centauri.academy.cerepro.persistence.entity.CeReProAbstractEntity;
import centauri.academy.cerepro.persistence.entity.Survey;
import centauri.academy.cerepro.persistence.entity.SurveyReply;
import centauri.academy.cerepro.persistence.entity.User;
import centauri.academy.cerepro.persistence.entity.custom.CustomErrorType;
import centauri.academy.cerepro.persistence.entity.custom.QuestionCustom;
import centauri.academy.cerepro.persistence.repository.SurveyRepository;
import centauri.academy.cerepro.persistence.repository.UserRepository;
import centauri.academy.cerepro.persistence.repository.candidate.CandidateRepository;
import centauri.academy.cerepro.persistence.repository.candidatesurveytoken.CandidateSurveyTokenRepository;
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
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;

	public boolean generatePdf(Long surveyReplyId) {
		
		logger.debug("################### SERVICE METHOD CALLED ###################");
		
		Optional<SurveyReply> surveyReply = surveyReplyRepository.findById(surveyReplyId);
		Optional<Survey> survey = surveyRepository.findById(surveyReply.get().getSurveyId());
		Optional<Candidate> candidate = candidateService.getById(surveyReply
				.get().getCandidateId());
		List<QuestionCustom> questionCustomList = questionService.getAllQuestionCustomListFromSurveyId(surveyReply.get().getSurveyId());
		List<QuestionAndReply> questionReplyList = null;
		
		try {
			questionReplyList = this.createQuestionReplyList(questionCustomList, surveyReply.get().getAnswers());
			System.out.println("######## QUESTION LIST è null? " + questionCustomList == null);
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
		String name = candidate.get().getFirstname() + "-" + candidate.get().getLastname()+"-" 
				+ surveyReply.get().getStarttime().getMonthValue() + "-" 
				+ surveyReply.get().getStarttime().getDayOfMonth() + "-" 
				+ surveyReply.get().getId() + ".pdf";
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
			if (pdfGenerated) {
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
		    String mess = "Ciao, in allegato il PDF generato al termine del questionario compilato da " + optCandidate.get().getFirstname() + optCandidate.get().getLastname() + ", candidato con ID: " + optCandidate.get().getId() + ".";
		    
		    String path = env.getProperty("app.folder.candidate.survey.pdf");
			String name = optCandidate.get().getFirstname() + "-" + optCandidate.get().getLastname() + "-" 
					+ surveyReply.getStarttime().getMonthValue() + "-" 
					+ surveyReply.getStarttime().getDayOfMonth() + "-" 
					+ surveyReply.getId() + ".pdf";
		    String attachmentPath = path.concat(File.separator).concat(name);
		    
		    String pdfFileName = surveyReply.getPdffilename();
		    String attachmentName = pdfFileName;
		    
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
		QuestionAndReply[] qarArray = gson.fromJson(answers, QuestionAndReply[].class); 
//		int i = 0;
		if(answers == null || answers.equals(""))
			return null;
		try {
//			List<QuestionAndReply> qaaq = mapper.readValue(answers, List<QuestionAndReply.class>);
			for(QuestionCustom es : questionCustomList) {
				logger.info("createQuestionReplyList - DEBUG - " + es);
				for (int i=0; i<qarArray.length; i++) {
					if (qarArray[i].getQuestionId()==es.getId()) {
						QuestionAndReply qar = new QuestionAndReply(es.getId(), es.getLabel(), es.getDescription(), 
								es.getAnsa(), es.getAnsb(), es.getAnsc(), es.getAnsd(), es.getAnse(), es.getAnsf(), 
								es.getAnsg(), es.getAnsh(), es.getCansa(), es.getCansb(), es.getCansc(), es.getCansd(), 
								es.getCanse(), es.getCansf(), es.getCansg(), es.getCansh() , es.getFullAnswer(), es.getPosition(),
								qarArray[i].getQuestionId(), qarArray[i].getUserCansa(), qarArray[i].getUserCansb(), qarArray[i].getUserCansc(), qarArray[i].getUserCansd(), qarArray[i].getUserCanse(),
								qarArray[i].getUserCansf(), qarArray[i].getUserCansg(), qarArray[i].getUserCansh());
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

		String s = "Candidato: ";
//		s += candidate.getClass().getSimpleName().toUpperCase() + "\n";
//		s += "ID: " + candidate.getId() + "\n";
//		s += "Course code: " + candidate.getCourseCode() + "\n";
//		s += "Email: " + candidate.getEmail() + "\n";
//		s += "Firstname: " + candidate.getFirstname() + "\n";
//		s += "Lastname: " + candidate.getLastname() + "\n";		
		s += candidate.getFirstname() + " " + candidate.getLastname() + "\n";
		s += "Email candidato: " + candidate.getEmail() + "\n";
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
		s += "Questionario svolto: " + survey.getLabel() + "(" + questionCustomList.size() + " domande)\n";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		s += "Giorno di esecuzione del questionario: " + surveyReply.getStarttime().format(formatter) + "\n";
		s += "Tempo massimo di esecuzione del questionario: " + survey.getTime() + " minuti\n";
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		s += "Inizio: " + surveyReply.getStarttime().format(formatter) + " - Fine: " + surveyReply.getEndtime().format(formatter) + "\n";
		s += "\n\n\n";
		lista.add(s);
		int i = 0;
		for (QuestionCustom rq : questionReplyList) {
				logger.info("####################### CREAZIONE STRINGA DENTRO FOR ###########");
				logger.info("####################### dimensione questionCustomList: " + questionCustomList.size());
				
				s = "";
//				s += "ID: " + rq.getId() + "\n";
				s += "Domanda numero: " + rq.getPosition() + " - " + rq.getLabel() + "\n";
				s += rq.getDescription() + "\n";
//				s += "Position: " + rq.getPosition() + "\n";
				if (rq.getAnsa()!=null) {
					s += "Risposta A: " + rq.getAnsa() + "(" + rq.getCansa() + "): " + questionReplyList.get(i).getCansa()+ "\n";
				}
				if (rq.getAnsb()!=null) {
				    s += "Risposta B: " + rq.getAnsb() + "(" + rq.getCansb() + "): " + questionReplyList.get(i).getCansb() + "\n";
				}
				if (rq.getAnsc()!=null) {
					s += "Risposta C: " + rq.getAnsc() + "(" + rq.getCansc() + "): " + questionReplyList.get(i).getCansc() + "\n";
				}
				if (rq.getAnsd()!=null) {
					s += "Risposta D: " + rq.getAnsd() + "(" + rq.getCansd() + "): " + questionReplyList.get(i).getCansd() + "\n";
				}
				if (rq.getAnse()!=null) {
					s += "Risposta E: " + rq.getAnse() + "(" + rq.getCanse() + "): " + questionReplyList.get(i).getCanse() + "\n";
				}
				if (rq.getAnsf()!=null) {
				    s += "Risposta F: " + rq.getAnsf() + "(" + rq.getCansf() + "): " + questionReplyList.get(i).getCansf() + "\n";
				}
				if (rq.getAnsg()!=null) {
				    s += "Risposta G: " + rq.getAnsg() + "(" + rq.getCansg() + "): " + questionReplyList.get(i).getCansg() + "\n";
				}
				if (rq.getAnsh()!=null) {
					s += "Risposta H: " + rq.getAnsh() + "(" + rq.getCansh() + "): " + questionReplyList.get(i).getCansh() + "\n";
				}
				s += "\n";
				s += "Risposta completa/descrittiva: " + rq.getFullAnswer() + "\n\n\n\n";
				logger.info("####### String creata: " + s);
				lista.add(s);
				i++;
			
		}
		
		s = "Al termine del questionario il punteggio complessivo totalizzato dal candidato è di " + surveyReply.getPoints() + " punti\n";
//		s += "Answers:" + surveyReply.getAnswers() + "\n\n\n\n\n";
		lista.add(s);

		return lista;
	}
}
