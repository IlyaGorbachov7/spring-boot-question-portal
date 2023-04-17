package softarex.gorbachev.springbootquestionportal.service.rest.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;
import softarex.gorbachev.springbootquestionportal.service.rest.AnswerTypeRestService;
import softarex.gorbachev.springbootquestionportal.service.AnswerTypeService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnswerTypeRestServiceImpl implements AnswerTypeRestService {

    private AnswerTypeService answerTypeService;

    public ResponseEntity<List<AnswerTypeDto>> getAllAnswerType() {
        return new ResponseEntity<>(answerTypeService.getAllAnswerType(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AnswerTypeDto> getAnswerTypeById(UUID id) {
        return new ResponseEntity<>(answerTypeService.getAnswerTypeById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AnswerTypeDto> getAnswerTypeByName(AnswerTypeDto answerTypeDto) {
        return new ResponseEntity<>(answerTypeService.getAnswerTypeByName(answerTypeDto),HttpStatus.OK);
    }

}
