package imd.eventhub.restAPI.infra;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import imd.eventhub.exception.BadRequestException;
import imd.eventhub.exception.NotFoundException;
import lombok.Data;

@RestControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorMessage handleNotFoundException(BadRequestException ex){
        return new RestErrorMessage(400, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestErrorMessage handleNotFoundException(NotFoundException ex){
        return new RestErrorMessage(404, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails(400, HttpStatus.BAD_REQUEST, "Erro de validação", errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }

    @Data
    public class ErrorDetails {
        private Integer code;
        private HttpStatus status;
        private String message;
        private List<String> errors;
    
        public ErrorDetails(Integer code, HttpStatus status, String message, List<String> errors) {
            super();
            this.code = code;
            this.status = status;
            this.message = message;
            this.errors = errors;
        }
    
        public ErrorDetails(Integer code,HttpStatus status, String message, String error) {
            super();
            this.code = code;
            this.status = status;
            this.message = message;
            errors = Arrays.asList(error);
        }
    }
}
