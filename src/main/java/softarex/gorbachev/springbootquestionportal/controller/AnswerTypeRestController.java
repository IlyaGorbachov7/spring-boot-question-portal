package softarex.gorbachev.springbootquestionportal.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;
import softarex.gorbachev.springbootquestionportal.service.rest.AnswerTypeRestService;

import java.util.List;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_ALL;
import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.AnswerTypeRequestMappingConst.*;

@RestController
@RequestMapping(ANSWER_TYPES_CONTROLLER)
@CrossOrigin(value = {CROSS_ORIGIN_LOCALHOST3000,CROSS_ORIGIN_ALL})
@AllArgsConstructor
public class AnswerTypeRestController {

    private AnswerTypeRestService answerTypeRestService;

    @GetMapping(ANSWER_TYPES)
    public ResponseEntity<List<AnswerTypeDto>> getAllAnswerType() {
        return answerTypeRestService.getAllAnswerType();
    }

    @GetMapping(ANSWER_TYPES_PATHVARIABLE_ID)
    public ResponseEntity<AnswerTypeDto> getAnswerTypeById(@PathVariable String id) {
        return answerTypeRestService.getAnswerTypeById(id);
    }

    @GetMapping(ANSWER_TYPES)
    public ResponseEntity<AnswerTypeDto> getAnswerTypeByName(AnswerTypeDto answerTypeDto){
        return answerTypeRestService.getAnswerTypeByName(answerTypeDto);
    }
}
