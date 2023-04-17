package softarex.gorbachev.springbootquestionportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AnswerTypeMapper {

    public abstract AnswerTypeDto answerToAnswerDto(AnswerType answerType);

    public abstract List<AnswerTypeDto> answerTypesToDto(List<AnswerType> answerTypes);

}
