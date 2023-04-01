package softarex.gorbachev.springbootquestionportal.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;
import softarex.gorbachev.springbootquestionportal.exception.confcode.ChangeConfigurationCodeExistUserException;
import softarex.gorbachev.springbootquestionportal.exception.confcode.NoFoundConfigurerCodeForUserEmail;
import softarex.gorbachev.springbootquestionportal.exception.confcode.NoMatchesBetweenConfigurerCodeException;
import softarex.gorbachev.springbootquestionportal.mapper.PasswordConfigurationCodeMapper;
import softarex.gorbachev.springbootquestionportal.utils.PasswordGenerator;
import softarex.gorbachev.springbootquestionportal.repository.PasswordTokenRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PasswordConfigurerCodeService {

    private final PasswordTokenRepository tokenRepository;

    private final PasswordConfigurationCodeMapper tokenMapper;

    private final PasswordGenerator passwordGenerator;

    private final EntityManager entityManager;

    @Value("${email.expiry-datetime}")
    private long expiryDatetime;

    @Value("${email.expiry-datetime.type}")
    private String chronoUnit;

    public PasswordConfigurerCode createConfigurerCode(User user) {
        tokenRepository.findByUserEmail(user.getEmail())
                .ifPresent((configureCode) -> {
                    if (!LocalDateTime.now().isAfter(configureCode.getExpiryDateTime())) {
                        throw new ChangeConfigurationCodeExistUserException(user.getEmail(), expiryDatetime, chronoUnit);
                    } else {
                        tokenRepository.delete(configureCode);
                        entityManager.flush();
                    }
                });
        PasswordConfigurerCode passwordConfigurerCode;
        try {
            String code = passwordGenerator.generate(6);
            passwordConfigurerCode = tokenMapper.toPasswordToken(user, code,
                    LocalDateTime.now().plus(expiryDatetime, ChronoUnit.valueOf(chronoUnit)));
            tokenRepository.save(passwordConfigurerCode);
        } catch (Exception e) {
            throw new QuestionPortalServerException("Error", e);
        }
        return passwordConfigurerCode;
    }

    public void deleteByUser(User user) {
        tokenRepository.deleteByUser(user);
    }

    public PasswordConfigurerCode findConfigurerCodeByUserEmail(User user) {
        return tokenRepository.findByUserEmail(user.getEmail())
                .orElseThrow(NoFoundConfigurerCodeForUserEmail::new);
    }

    public PasswordConfigurerCode findConfigurerCodeByCode(String code) {
        return tokenRepository.findByCode(code)
                .orElseThrow(NoMatchesBetweenConfigurerCodeException::new);
    }
}
