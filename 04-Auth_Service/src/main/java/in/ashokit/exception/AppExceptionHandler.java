package in.ashokit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value = AuthServiceException.class)
	public ResponseEntity<ExResponse> handleAuthServiceEx(AuthServiceException ae){
		
		ExResponse exResponse = new ExResponse(); 
		exResponse.setErrCode(ae.getErrCode());
		exResponse.setErrMsg(ae.getMessage());
		
		return new ResponseEntity<>(exResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
