package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.User;

import java.util.List;

public interface QuestionsRepository extends CrudRepository<Question, String> {

    List<Question> findAllByFromUser(User fromUser);

    List<Question> findAllByForUser(User forUser);

    List<Question> findAllByFromUser(User fromUser, Pageable pageable);

    List<Question> findAllByForUser(User forUser, Pageable pageable);

    Long countByFromUser(User fromUser);

    Long countByForUser(User forUser);


}
