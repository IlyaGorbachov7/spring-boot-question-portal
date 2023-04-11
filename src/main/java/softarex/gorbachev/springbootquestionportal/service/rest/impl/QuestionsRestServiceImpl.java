package softarex.gorbachev.springbootquestionportal.service.rest.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.service.QuestionsService;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageCreatedQuestResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.QuestionsRestService;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class QuestionsRestServiceImpl implements QuestionsRestService {

    private final QuestionsService questionsService;

    @Override
    public ResponseEntity<MessageCreatedQuestResponse> create(QuestionForUserDto questionDto, UserDetailsImpl fromUserAuth) {
        return new ResponseEntity<>(new MessageCreatedQuestResponse("Question is successfully created.",
                questionsService.create(questionDto, fromUserAuth.getTarget())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> update(QuestionForUserDto questionForUserDto, UserDetailsImpl fromUserAuth) {
        questionsService.update(questionForUserDto, fromUserAuth.getTarget());
        return new ResponseEntity<>(new MessageResponse("Question is successfully updated."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> answerQuestion(QuestionFromUserDto questionFromUserDto, UserDetailsImpl forUserAuth) {
        questionsService.answerQuestion(questionFromUserDto, forUserAuth.getTarget());
        return new ResponseEntity<>(new MessageResponse("Question is answered"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> delete(String id) {
        questionsService.delete(id);
        return new ResponseEntity<>(new MessageResponse("Question is successfully deleted."),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsFromUser(Integer page, Integer limit, UserDetailsImpl auth) {
        return new ResponseEntity<>(questionsService.getLimitNumberQuestionsFromUser(page, limit, auth.getTarget()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsForUser(Integer page, Integer limit, UserDetailsImpl auth) {
        return new ResponseEntity<>(questionsService.getLimitNumberQuestionsForUser(page, limit, auth.getTarget()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getALLQuestionsFromUser(UserDetailsImpl auth) {
        return new ResponseEntity<List<QuestionDto>>(questionsService.getALLQuestionsFromUser(auth.getTarget()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getALLQuestionsForUser(UserDetailsImpl auth) {
        return new ResponseEntity<>(questionsService.getALLQuestionsForUser(auth.getTarget()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> getQuantityQuestionFromUser(UserDetailsImpl auth) {
        return new ResponseEntity<>(questionsService.getQuantityQuestionFromUser(auth.getTarget()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Long> getQuantityQuestionForUser(UserDetailsImpl auth) {
        return new ResponseEntity<>(questionsService.getQuantityQuestionForUser(auth.getTarget()),
                HttpStatus.OK);
    }
}
