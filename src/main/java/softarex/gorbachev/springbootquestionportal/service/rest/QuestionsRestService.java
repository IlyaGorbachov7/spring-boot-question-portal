package softarex.gorbachev.springbootquestionportal.service.rest;

import org.springframework.http.ResponseEntity;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageCreatedQuestResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionsRestService {


    ResponseEntity<MessageCreatedQuestResponse> create(QuestionForUserDto questionDto, UserDetailsImpl fromUserAuth);

    ResponseEntity<MessageResponse> update(QuestionForUserDto questionForUserDto, UserDetailsImpl fromUserAuth);

    ResponseEntity<MessageResponse> answerQuestion(QuestionFromUserDto questionFromUserDto, UserDetailsImpl forUserAuth);

    ResponseEntity<MessageResponse> delete(UUID id);

    ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsFromUser(Integer page, Integer limit, UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsForUser(Integer page, Integer limit, UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getALLQuestionsFromUser(UserDetailsImpl auth);

    ResponseEntity<List<QuestionDto>> getALLQuestionsForUser(UserDetailsImpl auth);

    ResponseEntity<Long> getQuantityQuestionFromUser(UserDetailsImpl auth);

    ResponseEntity<Long> getQuantityQuestionForUser(UserDetailsImpl auth);

    ResponseEntity<Long> getQuantityQuestionFromToForUser(UserDetailsImpl fromAuth, String forEmail);
}
