package in.ashokit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExHandler {

	@ExceptionHandler(value = CartServiceException.class)
	public ResponseEntity<ErrorResponse> handleCartServiceEx(CartServiceException cse){
		ErrorResponse response = new ErrorResponse();
		
		response.setErrCode(cse.getErrCode());
		response.setErrMessage(cse.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
