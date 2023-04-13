package softarex.gorbachev.springbootquestionportal.service.rest;

import org.springframework.http.ResponseEntity;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;

import java.util.List;
import java.util.UUID;

public interface AnswerTypeRestService {


    ResponseEntity<List<AnswerTypeDto>> getAllAnswerType();

    ResponseEntity<AnswerTypeDto> getAnswerTypeById(UUID id);

    ResponseEntity<AnswerTypeDto> getAnswerTypeByName(AnswerTypeDto answerTypeDto);
}
