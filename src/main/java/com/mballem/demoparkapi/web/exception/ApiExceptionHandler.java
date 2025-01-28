package com.mballem.demoparkapi.web.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mballem.demoparkapi.exception.AvailableSpotException;
import com.mballem.demoparkapi.exception.ClientCpfNotFoundException;
import com.mballem.demoparkapi.exception.ClientIdNotFoundException;
import com.mballem.demoparkapi.exception.CodeUniqueViolationException;
import com.mballem.demoparkapi.exception.CpfUniqueViolationException;
import com.mballem.demoparkapi.exception.InvalidPasswordException;
import com.mballem.demoparkapi.exception.PasswordDoesNotMatchException;
import com.mballem.demoparkapi.exception.ReceiptNotFoundException;
import com.mballem.demoparkapi.exception.SpotCodeNotFoundException;
import com.mballem.demoparkapi.exception.UserIdNotFoundException;
import com.mballem.demoparkapi.exception.UsernameNotFoundException;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {
	
	private final MessageSource messageSource;
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorMessage> usernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getUsername()};
		String message = messageSource.getMessage("exception.usernameNotFoundException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request){
		String message = messageSource.getMessage("exception.invalidPasswordException", null, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, message));
	}
	
	@ExceptionHandler(PasswordDoesNotMatchException.class)
	public ResponseEntity<ErrorMessage> passwordDoesNotMatchException(RuntimeException ex, HttpServletRequest request){
		String message = messageSource.getMessage("exception.passwordDoesNotMatchException", null, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, message));
	}
	
	@ExceptionHandler(UserIdNotFoundException.class)
	public ResponseEntity<ErrorMessage> userIdNotFoundException(UserIdNotFoundException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getId()};
		String message = messageSource.getMessage("exception.userIdNotFoundException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(UsernameUniqueViolationException.class)
	public ResponseEntity<ErrorMessage> usernameUniqueViolationException(UsernameUniqueViolationException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getUsername()};
		String message = messageSource.getMessage("exception.usernameUniqueViolationException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
	}
	
	@ExceptionHandler(CpfUniqueViolationException.class)
	public ResponseEntity<ErrorMessage> cpfUniqueViolationException(CpfUniqueViolationException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getResource(), ex.getCode()};
		String message = messageSource.getMessage("exception.cpfUniqueViolationException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
	}
	
	@ExceptionHandler(ClientCpfNotFoundException.class)
	public ResponseEntity<ErrorMessage> clientCpfNotFoundException(ClientCpfNotFoundException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getCpf()};
		String message = messageSource.getMessage("exception.clientCpfNotFoundException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(ClientIdNotFoundException.class)
	public ResponseEntity<ErrorMessage> clientIdNotFoundException(ClientIdNotFoundException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getId()};
		String message = messageSource.getMessage("exception.clientIdNotFoundException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(ReceiptNotFoundException.class)
	public ResponseEntity<ErrorMessage> receiptNotFoundException(ReceiptNotFoundException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getReceipt()};
		String message = messageSource.getMessage("exception.receiptNotFoundException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(AvailableSpotException.class)
	public ResponseEntity<ErrorMessage> availableSpotException(RuntimeException ex, HttpServletRequest request){
		String message = messageSource.getMessage("exception.availableSpotException", null, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(SpotCodeNotFoundException.class)
	public ResponseEntity<ErrorMessage> spotCodeNotFoundException(SpotCodeNotFoundException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getSpotCode()};
		String message = messageSource.getMessage("exception.spotCodeNotFoundException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(CodeUniqueViolationException.class)
	public ResponseEntity<ErrorMessage> codeUniqueViolationException(CodeUniqueViolationException ex, HttpServletRequest request){
		Object[] params = new Object[] {ex.getCode()};
		String message = messageSource.getMessage("exception.codeUniqueViolationException", params, request.getLocale());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, message));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request){
		log.error("Api Error - ", ex);
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
																		HttpServletRequest request, 				
																		BindingResult result){
		log.error("Api Error - ", ex);
		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(
						request, HttpStatus.UNPROCESSABLE_ENTITY, messageSource.getMessage("message.invalid.field", null, request.getLocale()), result, messageSource));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex, HttpServletRequest request) {
		ErrorMessage error = new ErrorMessage(
				request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		log.error("Internal Server Error {} {} ", error, ex.getMessage());
		return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.contentType(MediaType.APPLICATION_JSON)
					.body(error);
	}

}
