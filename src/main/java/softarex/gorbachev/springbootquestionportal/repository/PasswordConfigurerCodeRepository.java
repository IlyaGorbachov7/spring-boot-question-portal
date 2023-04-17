package softarex.gorbachev.springbootquestionportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;

import java.util.Optional;
import java.util.UUID;

public interface PasswordConfigurerCodeRepository extends CrudRepository<PasswordConfigurerCode, UUID> {
    @Transactional
    @Override
    void delete(PasswordConfigurerCode entity);

    @Query(value = "select t from PasswordConfigurerCode t where t.user.email = :email")
    Optional<PasswordConfigurerCode> findByUserEmail(String email);

    Optional<PasswordConfigurerCode> findByCode(String code);

    // join query
    @Query(value = "select t from PasswordConfigurerCode t where t.code = :code and t.user.email = :email ")
    Optional<PasswordConfigurerCode> findByCodeAndUserEmail(String code, String email);
}
