package softarex.gorbachev.springbootquestionportal.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionFromUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserDto;
import softarex.gorbachev.springbootquestionportal.exception.quest.QuestionNotFounded;
import softarex.gorbachev.springbootquestionportal.mapper.QuestionMapper;
import softarex.gorbachev.springbootquestionportal.repository.QuestionsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionsService {

    private final QuestionsRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final UserService userService;

    public String create(QuestionForUserDto questForDto, UserDto fromUserDto) {
        Question question = questionMapper
                .questDtoToQuestion(questionMapper
                        .questForUserDtoToQuestDto(questForDto, fromUserDto.getEmail()));
        questionRepository.save(question);
        return question.getId();
    }

    public void delete(String id) {
        Question question = findById(id);
        questionRepository.delete(question);
    }

    public void update(QuestionForUserDto questForUserDto, UserDto fromUser) {
        Question question = findById(questForUserDto.getId());
        questionMapper.updateQuestion(question, questionMapper
                .questForUserDtoToQuestDto(questForUserDto, fromUser.getEmail()));
    }

    public void answerQuestion(QuestionFromUserDto questionFromUserDto, UserDto forUserDto) {
        Question question = findById(questionFromUserDto.getId());
        questionMapper.updateQuestion(question, questionMapper
                .questFromUserDtoToQuestDto(questionFromUserDto, forUserDto.getEmail()));
    }


    public List<QuestionDto> getLimitNumberQuestionsFromUser(Integer page, Integer limit, UserDto userDto) {
        return questionMapper.questionsToDto(questionRepository
                .findAllByFromUser(userService.findUserEntityByEmail(userDto.getEmail()), PageRequest.of(page, limit)));
    }

    public List<QuestionDto> getLimitNumberQuestionsForUser(Integer page, Integer limit, UserDto userDto) {
        return questionMapper.questionsToDto(questionRepository
                .findAllByForUser(userService.findUserEntityByEmail(userDto.getEmail()), PageRequest.of(page, limit)));
    }

    public List<QuestionDto> getALLQuestionsFromUser(UserDto userDto) {
        return questionMapper.questionsToDto(questionRepository
                .findAllByFromUser(userService.findUserEntityByEmail(userDto.getEmail())));
    }

    public List<QuestionDto> getALLQuestionsForUser(UserDto userDto) {
        return questionMapper.questionsToDto(questionRepository
                .findAllByForUser(userService.findUserEntityByEmail(userDto.getEmail())));
    }

    public Long getQuantityQuestionFromUser(UserDto userDto) {
        return questionRepository.countByFromUser(userService
                .findUserEntityByEmail(userDto.getEmail()));
    }

    public Long getQuantityQuestionForUser(UserDto userDto) {
        return questionRepository.countByForUser(userService
                .findUserEntityByEmail(userDto.getEmail()));
    }

    private Question findById(String id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFounded(id));
    }
}
