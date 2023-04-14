package softarex.gorbachev.springbootquestionportal.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerTypeDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-14T20:32:07+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class AnswerTypeMapperImpl extends AnswerTypeMapper {

    @Override
    public AnswerTypeDto answerToAnswerDto(AnswerType answerType) {
        if ( answerType == null ) {
            return null;
        }

        AnswerTypeDto answerTypeDto = new AnswerTypeDto();

        if ( answerType.getId() != null ) {
            answerTypeDto.setId( answerType.getId().toString() );
        }
        answerTypeDto.setNameType( answerType.getNameType() );

        return answerTypeDto;
    }

    @Override
    public List<AnswerTypeDto> answerTypesToDto(List<AnswerType> answerTypes) {
        if ( answerTypes == null ) {
            return null;
        }

        List<AnswerTypeDto> list = new ArrayList<AnswerTypeDto>( answerTypes.size() );
        for ( AnswerType answerType : answerTypes ) {
            list.add( answerToAnswerDto( answerType ) );
        }

        return list;
    }
}
