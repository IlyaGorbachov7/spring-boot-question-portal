package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.User;

import java.util.Optional;

public interface PasswordTokenRepository extends CrudRepository<PasswordConfigurerCode, Long> {
    @Transactional
    @Override
    void delete(PasswordConfigurerCode entity);

    void deleteByUser(User user);

    @Query(value = "select t from PasswordConfigurerCode t where t.user.email = :email")
    Optional<PasswordConfigurerCode> findByUserEmail(String email);

    Optional<PasswordConfigurerCode> findByCode(String code);
}
