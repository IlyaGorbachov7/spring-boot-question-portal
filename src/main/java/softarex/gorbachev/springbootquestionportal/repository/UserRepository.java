package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import softarex.gorbachev.springbootquestionportal.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findAllByEmailNot(String email);
}
