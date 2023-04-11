package softarex.gorbachev.springbootquestionportal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;
import softarex.gorbachev.springbootquestionportal.exception.anwertype.AnswerTypeNotFoundException;
import softarex.gorbachev.springbootquestionportal.mapper.AnswerTypeMapper;
import softarex.gorbachev.springbootquestionportal.repository.AnswerTypeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerTypeService {

    private AnswerTypeRepository answerRepository;

    private AnswerTypeMapper answerMapper;

    public List<AnswerTypeDto> getAllAnswerType() {
        return answerMapper.answerTypesToDto(answerRepository.findAll());
    }

    public AnswerTypeDto getAnswerTypeById(String id) {
        return answerRepository.findById(id)
                .map(answerMapper::answerToAnswerDto)
                .orElseThrow(AnswerTypeNotFoundException::new);
    }

    public AnswerTypeDto getAnswerTypeByName(AnswerTypeDto answerTypeDto) {
        return answerRepository.findByNameType(answerTypeDto.getNameType())
                .map(answerMapper::answerToAnswerDto)
                .orElseThrow(AnswerTypeNotFoundException::new);
    }

    public AnswerType getAnswerTypeEntityById(String id) {
        return answerRepository.findById(id)
                .orElseThrow(AnswerTypeNotFoundException::new);
    }

    public AnswerType getAnswerTypeEntityByName(String nameType) {
        return answerRepository.findByNameType(nameType)
                .orElseThrow(AnswerTypeNotFoundException::new);
    }
}
