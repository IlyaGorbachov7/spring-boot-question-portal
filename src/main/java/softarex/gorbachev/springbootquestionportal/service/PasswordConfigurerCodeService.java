package softarex.gorbachev.springbootquestionportal.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.PasswordConfigurerCodeDto;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;
import softarex.gorbachev.springbootquestionportal.exception.confcode.ChangeConfigurationCodeExistUserException;
import softarex.gorbachev.springbootquestionportal.exception.confcode.ConfigurerCodeException;
import softarex.gorbachev.springbootquestionportal.mapper.PasswordConfigurationCodeMapper;
import softarex.gorbachev.springbootquestionportal.utils.PasswordGenerator;
import softarex.gorbachev.springbootquestionportal.repository.PasswordConfigurerCodeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordConfigurerCodeService {

    private final PasswordConfigurerCodeRepository passwordConfigCodeRepository;

    private final PasswordConfigurationCodeMapper passwordConfigCodeMapper;

    private final PasswordGenerator passwordGenerator;

    private final EntityManager entityManager;

    @Value("${email.expiry-datetime}")
    private long expiryDatetime = 5;

    @Value("${email.expiry-datetime.type}")
    private String chronoUnit = "HOURS";

    public PasswordConfigurerCodeDto createConfigurerCode(User user) {
        passwordConfigCodeRepository.findByUserEmail(user.getEmail())
                .ifPresent((configureCodeEntity) -> {
                    if (!LocalDateTime.now().isAfter(configureCodeEntity.getExpiryDateTime())) {
                        throw new ChangeConfigurationCodeExistUserException(user.getEmail(), expiryDatetime, chronoUnit);
                    } else {
                        passwordConfigCodeRepository.delete(configureCodeEntity);
                        entityManager.flush();
                    }
                });
        PasswordConfigurerCode passwordConfigurerCode;
        try {
            String code = passwordGenerator.generate(6);
            passwordConfigurerCode = passwordConfigCodeMapper.toPasswordConfigurerCode(user, code,
                    LocalDateTime.now().plus(expiryDatetime, ChronoUnit.valueOf(chronoUnit)));
            passwordConfigCodeRepository.save(passwordConfigurerCode);
        } catch (Exception e) {
            throw new QuestionPortalServerException("Error", e);
        }
        return passwordConfigCodeMapper.passwordConfCodeToPasswordConfCodeDto(passwordConfigurerCode);
    }

    public PasswordConfigurerCode findConfigurerCodeByCodeAndUserEmail(String code, String email) {
        return passwordConfigCodeRepository.findByCodeAndUserEmail(code, email)
                .orElseThrow(() -> new ConfigurerCodeException(email));
    }

    public void deleteById(UUID id) {
        passwordConfigCodeRepository.deleteById(id);
    }
}
