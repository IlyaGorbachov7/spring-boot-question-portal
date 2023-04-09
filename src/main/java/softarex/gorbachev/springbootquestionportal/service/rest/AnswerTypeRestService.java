package softarex.gorbachev.springbootquestionportal.service.rest;

import org.springframework.http.ResponseEntity;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;

import java.util.List;

public interface AnswerTypeRestService {


    ResponseEntity<List<AnswerTypeDto>> getAllAnswerType();

    ResponseEntity<AnswerTypeDto> getAnswerTypeById(String id);
}
