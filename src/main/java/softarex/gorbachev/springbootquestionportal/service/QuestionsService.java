package softarex.gorbachev.springbootquestionportal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.dto.QuestionForUserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserDto;
import softarex.gorbachev.springbootquestionportal.mapper.QuestionMapper;
import softarex.gorbachev.springbootquestionportal.repository.QuestionsRepository;

@Service
@AllArgsConstructor
public class QuestionsService {

    private QuestionsRepository questionRepository;

    private QuestionMapper questionMapper;

    public void create(QuestionForUserDto questionDto, UserDto auth) {

    }

    public void delete(String id){

    }

    public void update(QuestionForUserDto questionDto, UserDto auth){

    }

    public Long getQuantityQuestionFromUser(UserDto auth) {

        return null;
    }
}
