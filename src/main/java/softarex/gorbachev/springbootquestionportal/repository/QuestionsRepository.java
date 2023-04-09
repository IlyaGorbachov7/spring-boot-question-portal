package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.repository.CrudRepository;
import softarex.gorbachev.springbootquestionportal.entity.Question;

public interface QuestionsRepository extends CrudRepository<Question, String> {

}
