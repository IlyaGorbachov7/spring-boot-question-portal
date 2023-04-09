package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.repository.CrudRepository;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;

import java.util.List;

public interface AnswerTypeRepository extends CrudRepository<AnswerType, String> {
    @Override
    List<AnswerType> findAll();
}
