package softarex.gorbachev.springbootquestionportal.service.rest.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.service.QuestionsService;
import softarex.gorbachev.springbootquestionportal.service.rest.QuestionsRestService;

@Service
@AllArgsConstructor
public class QuestionsRestServiceImpl implements QuestionsRestService {

  private QuestionsService questionsService;


}
