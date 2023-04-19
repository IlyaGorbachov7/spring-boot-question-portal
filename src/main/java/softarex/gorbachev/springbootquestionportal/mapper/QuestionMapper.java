package softarex.gorbachev.springbootquestionportal.mapper;

import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.AnswerQuestDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.service.AnswerTypeService;
import softarex.gorbachev.springbootquestionportal.service.UserService;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private AnswerTypeService answerTypeService;

    @Mapping(target = "answerType", source = "question.answerType.nameType")
    @Mapping(target = "emailFromUser", source = "question.fromUser.email")
    @Mapping(target = "emailForUser", source = "question.forUser.email")
    public abstract QuestionDto questionToDto(Question question);

    public abstract List<QuestionDto> questionsToDto(List<Question> questions);

    public Question questDtoToQuestion(QuestionDto questDto) {
        return assembleEntity(
                userService.findUserEntityByEmail(questDto.getEmailFromUser()),
                userService.findUserEntityByEmail(questDto.getEmailForUser()),
                answerTypeService.getAnswerTypeEntityByName(questDto.getAnswerType()),
                questDto
        );
    }

    @Mapping(target = "id", source = "questDto.id")
    @Mapping(target = "answerType", source = "answerType")
    abstract Question assembleEntity(User fromUser, User forUser,
                                     AnswerType answerType, QuestionDto questDto);

    public abstract QuestionDto questForUserDtoToQuestDto(QuestionForUserDto quest, String emailFromUser);

    public abstract QuestionDto questFromUserDtoToQuestDto(QuestionFromUserDto quest, String emailForUser);

    public void updateQuestion(Question entity, QuestionDto questDto) {
        Question resource = assembleEntity(
                userService.findUserEntityByEmail(questDto.getEmailFromUser()),
                userService.findUserEntityByEmail(questDto.getEmailForUser()),
                answerTypeService.getAnswerTypeEntityByName(questDto.getAnswerType()),
                questDto
        );
        update(entity, resource);
    }

    abstract void update(@MappingTarget Question target, Question resource);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "answerText", source = "answerQuestDto.answerText")
    public abstract void updateAnswerQuest(@MappingTarget Question question, AnswerQuestDto answerQuestDto);
}
