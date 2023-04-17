package softarex.gorbachev.springbootquestionportal.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import softarex.gorbachev.springbootquestionportal.exception.login.LoginException;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;
import softarex.gorbachev.springbootquestionportal.service.mdls.BadMessageResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppRestExceptionHandler {

    @ExceptionHandler({QuestionPortalServerException.class})
    public ResponseEntity<BadMessageResponse> handlerGeneralExceptions(QuestionPortalServerException ex) {
        return ResponseEntity.badRequest().body(new BadMessageResponse(ex.getMessage()));
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> map.put(fieldError.getField(),
                fieldError.getDefaultMessage()));

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> map = new HashMap<>();
        e.getConstraintViolations().forEach(violation -> map.put(violation.getPropertyPath().toString(),
                violation.getMessage()));
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
