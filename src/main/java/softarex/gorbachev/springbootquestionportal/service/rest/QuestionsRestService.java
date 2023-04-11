package softarex.gorbachev.springbootquestionportal.service.rest;

import org.springframework.http.ResponseEntity;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;

import java.util.List;

public interface QuestionsRestService {


    ResponseEntity<MessageResponse> create(QuestionForUserDto questionDto, UserDetailsImpl auth);

    ResponseEntity<MessageResponse> delete(String id);

    ResponseEntity<MessageResponse> update(QuestionForUserDto questionForUserDto, UserDetailsImpl auth);

    ResponseEntity<Long> getQuantityQuestionFromUser(UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsFromUser(Integer page, Integer limit, UserDetailsImpl auth);

    ResponseEntity<MessageResponse> answerQuestion(QuestionFromUserDto questionFromUserDto, UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getALLQuestionsFromUser(UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getALLQuestionsForUser(UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsForUser(Integer page, Integer limit, UserDetailsImpl auth);

    ResponseEntity<Long> getQuantityQuestionForUser(UserDetailsImpl auth);
}
