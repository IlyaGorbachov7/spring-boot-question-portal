package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerTypeRepository extends JpaRepository<AnswerType, UUID> {
    @Override
    List<AnswerType> findAll(Sort sort);

    Optional<AnswerType> findByNameType(String nameType);
}
