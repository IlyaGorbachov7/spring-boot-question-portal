package softarex.gorbachev.springbootquestionportal.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageCreatedQuestResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.QuestionsRestService;

import java.util.List;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_ALL;
import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.QuestionRequestMappingConst.*;

@RestController
@RequestMapping(QUESTIONS_CONTROLLER)
@CrossOrigin(value = {CROSS_ORIGIN_LOCALHOST3000, CROSS_ORIGIN_ALL})
@AllArgsConstructor
public class QuestionRestController {

    private final QuestionsRestService questionRestService;

    @PostMapping(QUESTIONS) // должен возвращаться id вопроса !!!
    public ResponseEntity<MessageCreatedQuestResponse> create(@RequestBody QuestionForUserDto questionDto,
                                                              @AuthenticationPrincipal UserDetailsImpl auth) { // question from-me
        return questionRestService.create(questionDto, auth);
    }

    @PutMapping(value = QUESTIONS)
    public ResponseEntity<MessageResponse> update(@RequestBody QuestionForUserDto questionForUserDto,
                                                  @AuthenticationPrincipal UserDetailsImpl auth) { // question from-me
        return questionRestService.update(questionForUserDto, auth);
    }

    @DeleteMapping(value = QUESTIONS_ID_PV)
    public ResponseEntity<MessageResponse> delete(@PathVariable String id) {
        return questionRestService.delete(id);
    }

    @PatchMapping(value = QUESTIONS)
    public ResponseEntity<MessageResponse> answerTheQuestion(@RequestBody QuestionFromUserDto questionFromUserDto,
                                                             @AuthenticationPrincipal UserDetailsImpl auth) {// question for-me
        return questionRestService.answerQuestion(questionFromUserDto, auth);
    }

    @GetMapping(QUESTIONS_FROM_ME)
    public ResponseEntity<List<QuestionDto>> receiveALLQuestionsFromUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getALLQuestionsFromUser(auth);
    }

    @GetMapping(QUESTIONS_FOR_ME)
    public ResponseEntity<List<QuestionDto>> receiveALLQuestionsForUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getALLQuestionsForUser(auth);
    }

    @GetMapping(value = QUESTIONS_FROM_ME)
    public ResponseEntity<List<QuestionDto>> receiveLimitedNumberQuestionsFromUser(@RequestParam Integer page,
                                                                                   @RequestParam Integer limit,
                                                                                   @AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getLimitedNumberQuestionsFromUser(page, limit, auth);
    }


    @GetMapping(value= QUESTIONS_FOR_ME)
    public ResponseEntity<List<QuestionDto>> receiveLimitedNumberQuestionsForUser(@RequestParam Integer page,
                                                                                  @RequestParam Integer limit,
                                                                                  @AuthenticationPrincipal UserDetailsImpl auth){
        return questionRestService.getLimitedNumberQuestionsForUser(page,limit,auth);
    }

    @GetMapping((QUESTIONS_FROM_ME_QUANTITY))
    public ResponseEntity<Long> receiveQuantityQuestionFromUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getQuantityQuestionFromUser(auth);
    }


    @GetMapping(QUESTIONS_FOR_ME_QUANTITY)
    public ResponseEntity<Long> receiveQuantityQuestionForUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getQuantityQuestionForUser(auth);
    }

}
