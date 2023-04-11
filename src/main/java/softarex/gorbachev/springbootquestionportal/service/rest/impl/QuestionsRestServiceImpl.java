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
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.QuestionsRestService;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class QuestionsRestServiceImpl implements QuestionsRestService {

    private QuestionsService questionsService;

    @Override
    public ResponseEntity<MessageResponse> create(QuestionForUserDto questionDto, UserDetailsImpl auth) {
        questionsService.create(questionDto,auth.getTarget());
        return new ResponseEntity<>(new MessageResponse("Question is successfully created."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> delete(String id) {
        questionsService.delete(id);
        return new ResponseEntity<>(new MessageResponse("Question is successfully deleted."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> update(QuestionForUserDto questionForUserDto, UserDetailsImpl auth) {
        return null;
    }

    @Override
    public ResponseEntity<Long> getQuantityQuestionFromUser(UserDetailsImpl auth) {
        return new ResponseEntity<>(questionsService.getQuantityQuestionFromUser(auth.getTarget()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsFromUser(Integer page, Integer limit, UserDetailsImpl auth) {
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> answerQuestion(QuestionFromUserDto questionFromUserDto, UserDetailsImpl auth) {
        return null;
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getALLQuestionsFromUser(UserDetailsImpl auth) {
        return null;
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getALLQuestionsForUser(UserDetailsImpl auth) {
        return null;
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getLimitedNumberQuestionsForUser(Integer page, Integer limit, UserDetailsImpl auth) {
        return null;
    }

    @Override
    public ResponseEntity<Long> getQuantityQuestionForUser(UserDetailsImpl auth) {
        return null;
    }
}
