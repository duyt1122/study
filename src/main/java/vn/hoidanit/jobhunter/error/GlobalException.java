package vn.hoidanit.jobhunter.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.domain.response.RestResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(value = IdInvalidException.class)
	public ResponseEntity<RestResponse<Object>> handlerIdInvalidException(IdInvalidException ex) {

		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setError(ex.getMessage());
		res.setMessage("Exception occurs..");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<RestResponse<Object>> handlerMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> listField = result.getFieldErrors();
		List<String> listError = listField.stream().map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
		RestResponse<Object> res = new RestResponse<>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage(listError.size() > 1 ? listError : listError);
		res.setError("Exception occurs...");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
}
