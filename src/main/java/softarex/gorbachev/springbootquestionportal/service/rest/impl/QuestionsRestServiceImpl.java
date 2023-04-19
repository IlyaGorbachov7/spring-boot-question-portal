package softarex.gorbachev.springbootquestionportal.service.rest.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerQuestDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.service.QuestionsService;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageCreatedQuestResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.QuestionsRestService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class QuestionsRestServiceImpl implements QuestionsRestService {

    private final QuestionsService questionsService;

    @Transactional
    @Override
    public ResponseEntity<MessageCreatedQuestResponse> create(QuestionForUserDto questionDto, UserDetailsImpl fromUserAuth) {
        return new ResponseEntity<>(new MessageCreatedQuestResponse("Question is successfully created.",
                questionsService.create(questionDto, fromUserAuth.getTarget()).toString()), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<MessageResponse> update(QuestionForUserDto questionForUserDto, UserDetailsImpl fromUserAuth) {
        questionsService.update(questionForUserDto, fromUserAuth.getTarget());
        return new ResponseEntity<>(new MessageResponse("Question is successfully updated."), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<MessageResponse> answerQuestion(AnswerQuestDto answerQuestDto, UserDetailsImpl forUserAuth) {
        questionsService.answerQuestion(answerQuestDto, forUserAuth.getTarget());
        return new ResponseEntity<>(new MessageResponse("Question is answered"),
                HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<MessageResponse> delete(UUID id, UserDetailsImpl auth) {
        questionsService.delete(id, auth.getTarget());
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
        return new ResponseEntity<>(questionsService.getALLQuestionsFromUser(auth.getTarget()),
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

    @Override
    public ResponseEntity<Long> getQuantityQuestionFromToForUser(UserDetailsImpl fromAuth, String forEmail) {
        return new ResponseEntity<>(questionsService.getQuantityQuestionFromToForUser(fromAuth.getTarget(), forEmail), HttpStatus.OK);
    }
}
