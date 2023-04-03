package softarex.gorbachev.springbootquestionportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import softarex.gorbachev.springbootquestionportal.exception.login.LoginException;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;
import softarex.gorbachev.springbootquestionportal.service.mdls.BadMessageResponse;

@RestControllerAdvice
public class AppRestExceptionHandler {

    @ExceptionHandler({QuestionPortalServerException.class})
    public ResponseEntity<BadMessageResponse> handlerGeneralExceptions(QuestionPortalServerException ex) {
        return ResponseEntity.badRequest().body(new BadMessageResponse(ex.getMessage()));
    }
}
