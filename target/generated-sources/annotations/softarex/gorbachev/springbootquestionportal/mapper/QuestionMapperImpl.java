package softarex.gorbachev.springbootquestionportal.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-11T19:20:27+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl extends QuestionMapper {

    @Override
    public QuestionDto questionToDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        questionDto.setAnswerType( questionAnswerTypeNameType( question ) );
        questionDto.setEmailFromUser( questionFromUserEmail( question ) );
        questionDto.setEmailForUser( questionForUserEmail( question ) );
        questionDto.setId( question.getId() );
        questionDto.setQuestionText( question.getQuestionText() );
        questionDto.setAnswerText( question.getAnswerText() );
        questionDto.setOptions( question.getOptions() );

        return questionDto;
    }

    @Override
    public List<QuestionDto> questionsToDto(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionDto> list = new ArrayList<QuestionDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToDto( question ) );
        }

        return list;
    }

    @Override
    Question assembleEntity(User fromUser, User forUser, AnswerType answerType, QuestionDto questDto) {
        if ( fromUser == null && forUser == null && answerType == null && questDto == null ) {
            return null;
        }

        Question question = new Question();

        if ( questDto != null ) {
            question.setId( questDto.getId() );
            question.setQuestionText( questDto.getQuestionText() );
            question.setAnswerText( questDto.getAnswerText() );
            question.setOptions( questDto.getOptions() );
        }
        question.setFromUser( fromUser );
        question.setForUser( forUser );
        question.setAnswerType( answerType );

        return question;
    }

    @Override
    public QuestionDto questForUserDtoToQuestDto(QuestionForUserDto quest, String emailFromUser) {
        if ( quest == null && emailFromUser == null ) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        if ( quest != null ) {
            questionDto.setId( quest.getId() );
            questionDto.setQuestionText( quest.getQuestionText() );
            questionDto.setAnswerText( quest.getAnswerText() );
            questionDto.setAnswerType( quest.getAnswerType() );
            questionDto.setOptions( quest.getOptions() );
            questionDto.setEmailForUser( quest.getEmailForUser() );
        }
        questionDto.setEmailFromUser( emailFromUser );

        return questionDto;
    }

    @Override
    public QuestionDto questFromUserDtoToQuestDto(QuestionFromUserDto quest, String emailForUser) {
        if ( quest == null && emailForUser == null ) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        if ( quest != null ) {
            questionDto.setId( quest.getId() );
            questionDto.setQuestionText( quest.getQuestionText() );
            questionDto.setAnswerText( quest.getAnswerText() );
            questionDto.setAnswerType( quest.getAnswerType() );
            questionDto.setOptions( quest.getOptions() );
            questionDto.setEmailFromUser( quest.getEmailFromUser() );
        }
        questionDto.setEmailForUser( emailForUser );

        return questionDto;
    }

    @Override
    void update(Question target, Question resource) {
        if ( resource == null ) {
            return;
        }

        target.setId( resource.getId() );
        target.setQuestionText( resource.getQuestionText() );
        target.setAnswerText( resource.getAnswerText() );
        target.setAnswerType( resource.getAnswerType() );
        target.setOptions( resource.getOptions() );
        target.setFromUser( resource.getFromUser() );
        target.setForUser( resource.getForUser() );
    }

    private String questionAnswerTypeNameType(Question question) {
        if ( question == null ) {
            return null;
        }
        AnswerType answerType = question.getAnswerType();
        if ( answerType == null ) {
            return null;
        }
        String nameType = answerType.getNameType();
        if ( nameType == null ) {
            return null;
        }
        return nameType;
    }

    private String questionFromUserEmail(Question question) {
        if ( question == null ) {
            return null;
        }
        User fromUser = question.getFromUser();
        if ( fromUser == null ) {
            return null;
        }
        String email = fromUser.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String questionForUserEmail(Question question) {
        if ( question == null ) {
            return null;
        }
        User forUser = question.getForUser();
        if ( forUser == null ) {
            return null;
        }
        String email = forUser.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
