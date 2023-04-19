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
import softarex.gorbachev.springbootquestionportal.exception.quest.UserCanNotAddQuestionException;
import softarex.gorbachev.springbootquestionportal.exception.quest.UserCanNotChangeQuestionException;
import softarex.gorbachev.springbootquestionportal.exception.quest.UserCanNotDeleteQuestionException;
import softarex.gorbachev.springbootquestionportal.mapper.QuestionMapper;
import softarex.gorbachev.springbootquestionportal.repository.QuestionsRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionsService {

    private final QuestionsRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final UserService userService;

    public UUID create(QuestionForUserDto questForDto, UserDto fromUserDto) {
        if (questForDto.getEmailForUser().equals(fromUserDto.getEmail())) {
            throw new UserCanNotAddQuestionException(questForDto.getEmailForUser());
        }
        Question question = questionMapper
                .questDtoToQuestion(questionMapper
                        .questForUserDtoToQuestDto(questForDto, fromUserDto.getEmail()));
        questionRepository.save(question);
        return question.getId();
    }

    public void delete(UUID id, UserDto authDto) {
        Question question = findById(id);
        if (question.getFromUser().getEmail().equals(authDto.getEmail())) {
            questionRepository.delete(question);
        } else {
            throw new UserCanNotDeleteQuestionException(authDto.getEmail());
        }
    }

    public void update(QuestionForUserDto questForUserDto, UserDto fromUser) {
        Question question = findById(questForUserDto.getId());
        if (question.getFromUser().getEmail().equals(fromUser.getEmail()) &&
            !questForUserDto.getEmailForUser().equals(fromUser.getEmail())) {
            questionMapper.updateQuestion(question, questionMapper
                    .questForUserDtoToQuestDto(questForUserDto, fromUser.getEmail()));
        } else {
            throw new UserCanNotChangeQuestionException(fromUser.getEmail());
        }
    }

    public void answerQuestion(QuestionFromUserDto questionFromUserDto, UserDto forUserDto) {
        Question question = findById(questionFromUserDto.getId());
        if (question.getForUser().getEmail().equals(forUserDto.getEmail())) {
            questionMapper.updateQuestion(question, questionMapper
                    .questFromUserDtoToQuestDto(questionFromUserDto, forUserDto.getEmail()));
        } else {
            throw new UserCanNotChangeQuestionException(forUserDto.getEmail());
        }
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

    public List<Question> getALLQuestionsEntityForUser(UserDto userDto) {
        return questionRepository.findAllByForUser(userService
                .findUserEntityByEmail(userDto.getEmail()));
    }

    public Long getQuantityQuestionFromUser(UserDto userDto) {
        return questionRepository.countByFromUser(userService
                .findUserEntityByEmail(userDto.getEmail()));
    }

    public Long getQuantityQuestionForUser(UserDto userDto) {
        return questionRepository.countByForUser(userService
                .findUserEntityByEmail(userDto.getEmail()));
    }

    public Long getQuantityQuestionFromToForUser(UserDto fromUser, String forEmail) {
        return questionRepository.countByFromUserAndForUserEmail(userService
                .findUserEntityByEmail(fromUser.getEmail()), forEmail);
    }

    private Question findById(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFounded(id.toString()));
    }
}
