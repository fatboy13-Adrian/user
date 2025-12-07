package com.example.user.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity <String> handleUserIdNotFoundException(UserIdNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
	
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity <String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity <String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity <String> handleEmailNotFoundException(EmailNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity <String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(MobileNumberAlreadyExistsException.class)
	public ResponseEntity <String> handleMobileNumberAlreadyExistsException(MobileNumberAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(MobileNumberNotFoundException.class)
	public ResponseEntity <String> handleMobileNumberNotFoundException(MobileNumberNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity <String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}
}
