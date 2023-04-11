package softarex.gorbachev.springbootquestionportal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserDto;
import softarex.gorbachev.springbootquestionportal.mapper.QuestionMapper;
import softarex.gorbachev.springbootquestionportal.repository.QuestionsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionsService {

    private final QuestionsRepository questionRepository;

    private final QuestionMapper questionMapper;

    public String create(QuestionForUserDto questForDto, UserDto fromUserDto) {
        Question question = questionMapper
                .questDtoToQuestion(questionMapper
                        .questForUserDtoToQuestDto(questForDto, fromUserDto.getEmail()));
        questionRepository.save(question);
        return question.getId();
    }

    public void delete(String id) {

    }

    public void update(QuestionForUserDto questForUserDto, UserDto fromUser) {

    }

    public void answerQuestion(QuestionFromUserDto questionFromUserDto, UserDto forUserAuth) {
    }


    public List<QuestionDto> getLimitNumberQuestionsFromUser(Integer page, Integer limit, UserDto userDto) {
        return null;
    }

    public List<QuestionDto> getLimitNumberQuestionsForUser(Integer page, Integer limit, UserDto userDto) {
        return null;
    }

    public List<QuestionDto> getALLQuestionsFromUser(UserDto userDto) {
        return null;
    }

    public List<QuestionDto> getALLQuestionsForUser(UserDto userDto) {
        return null;
    }

    public Long getQuantityQuestionFromUser(UserDto userDto) {

        return null;
    }

    public Long getQuantityQuestionForUser(UserDto userDto) {
        return null;
    }

}
