package centauri.academy.cerepro.backend.validator;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestValidationHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(RestValidationHandler.class);
	
	@Autowired
	private MessageSource messageSource;
	

	// method to handle validation error
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<FieldValidationErrorDetails> handleValidationError(
			MethodArgumentNotValidException mNotValidException, HttpServletRequest request) {
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("WARNING");
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("#################");
		logger.warn("#################");
		FieldValidationErrorDetails fErrorDetails = new FieldValidationErrorDetails();
		fErrorDetails.setError_timeStamp(new Date().getTime());
		fErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
		fErrorDetails.setError_title("Field Validation Error");
		fErrorDetails.setError_detail("Inut Field Validation Failed");
		fErrorDetails.setError_developer_Message(mNotValidException.getClass().getName());
		fErrorDetails.setError_path(request.getRequestURI());

		BindingResult result = mNotValidException.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		for (FieldError error : fieldErrors) {
			FieldValidationError fError = processFieldError(error);
			List<FieldValidationError> fValidationErrorsList = fErrorDetails.getErrors().get(error.getField());
			if (fValidationErrorsList == null) {
				fValidationErrorsList = new ArrayList<FieldValidationError>();
			}
			fValidationErrorsList.add(fError);
			fErrorDetails.getErrors().put(error.getField(), fValidationErrorsList);
		}
		return new ResponseEntity<FieldValidationErrorDetails>(fErrorDetails, HttpStatus.BAD_REQUEST);
	}

	// method to process field error
	private FieldValidationError processFieldError(final FieldError error) {
		logger.warn("processFieldError - START - error: " + error);
		FieldValidationError fieldValidationError = new FieldValidationError();
		if (error != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			//logger.info("processFieldError - DEBUG - error.getDefaultMessage(): " + error.getDefaultMessage());
			String msg = messageSource.getMessage(
			error.getDefaultMessage(), null, currentLocale);
			fieldValidationError.setFiled(error.getField());
			fieldValidationError.setType(MessageType.ERROR);
			fieldValidationError.setMessage(msg);
		}
		logger.warn("processFieldError - END - fieldValidationError: " + fieldValidationError);
		return fieldValidationError;
	}

}
