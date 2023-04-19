package softarex.gorbachev.springbootquestionportal.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.constant.valid.OnCreate;
import softarex.gorbachev.springbootquestionportal.constant.valid.OnUpdate;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserEmailDto;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageCreatedQuestResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.QuestionsRestService;

import java.util.List;
import java.util.UUID;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_ALL;
import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.QuestionRequestMappingConst.*;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.WebSocketReqRespMappingConst.PRV_EMAIL_QUEST_CRUD;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.WebSocketReqRespMappingConst.PRV_QUEST_CRUD;

@RestController
@RequestMapping(QUESTIONS_CONTROLLER)
@CrossOrigin(value = {CROSS_ORIGIN_LOCALHOST3000, CROSS_ORIGIN_ALL})
@Validated
@AllArgsConstructor
public class QuestionRestController {

    private final QuestionsRestService questionRestService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(QUESTIONS)
    @Validated(OnCreate.class)
    public ResponseEntity<MessageCreatedQuestResponse> create(@RequestBody @Valid QuestionForUserDto questionDto,
                                                              @AuthenticationPrincipal UserDetailsImpl auth) { // question from-me
        return questionRestService.create(questionDto, auth);
    }

    @PutMapping(value = QUESTIONS)
    @Validated(OnUpdate.class)
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid QuestionForUserDto questionForUserDto,
                                                  @AuthenticationPrincipal UserDetailsImpl auth) { // question from-me
        return questionRestService.update(questionForUserDto, auth);
    }

    @DeleteMapping(value = QUESTIONS_ID_PV)
    public ResponseEntity<MessageResponse> delete(@PathVariable UUID id,
                                                  @AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.delete(id, auth);
    }

    @PatchMapping(value = QUESTIONS)
    @Validated(OnUpdate.class)
    public ResponseEntity<MessageResponse> answerTheQuestion(@RequestBody @Valid QuestionFromUserDto questionFromUserDto,
                                                             @AuthenticationPrincipal UserDetailsImpl auth) {// question for-me
        return questionRestService.answerQuestion(questionFromUserDto, auth);
    }

    @GetMapping(QUESTIONS_FROM_ME_ALL)
    public ResponseEntity<List<QuestionDto>> receiveALLQuestionsFromUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getALLQuestionsFromUser(auth);
    }

    @GetMapping(QUESTIONS_FOR_ME_ALL)
    public ResponseEntity<List<QuestionDto>> receiveALLQuestionsForUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getALLQuestionsForUser(auth);
    }

    @GetMapping(value = QUESTIONS_FROM_ME)
    public ResponseEntity<List<QuestionDto>> receiveLimitedNumberQuestionsFromUser(@RequestParam Integer page,
                                                                                   @RequestParam Integer limit,
                                                                                   @AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getLimitedNumberQuestionsFromUser(page, limit, auth);
    }


    @GetMapping(value = QUESTIONS_FOR_ME)
    public ResponseEntity<List<QuestionDto>> receiveLimitedNumberQuestionsForUser(@RequestParam Integer page,
                                                                                  @RequestParam Integer limit,
                                                                                  @AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getLimitedNumberQuestionsForUser(page, limit, auth);
    }

    @GetMapping((QUESTIONS_FROM_ME_QUANTITY))
    public ResponseEntity<Long> receiveQuantityQuestionFromUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getQuantityQuestionFromUser(auth);
    }


    @GetMapping(QUESTIONS_FOR_ME_QUANTITY)
    public ResponseEntity<Long> receiveQuantityQuestionForUser(@AuthenticationPrincipal UserDetailsImpl auth) {
        return questionRestService.getQuantityQuestionForUser(auth);
    }

    @GetMapping(QUESTIONS_FROM_ME_PVFOREMAIL_QUANTITY)
    public ResponseEntity<Long> receiveQuantityQuestionFromToForUser(@PathVariable(PV_FOREMAIL) String forEmail,
                                                                     @AuthenticationPrincipal UserDetailsImpl fromAuth) {
        return questionRestService.getQuantityQuestionFromToForUser(fromAuth, forEmail);
    }

    @MessageMapping(PRV_QUEST_CRUD)
    public void crudUserQuestions(@Payload UserEmailDto forUserDto) {
        simpMessagingTemplate.convertAndSend(String.format(PRV_EMAIL_QUEST_CRUD, forUserDto.getEmail()), forUserDto.getEmail());
    }
}
