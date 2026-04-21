package softarex.gorbachev.springbootquestionportal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.config.security.JWTTokenHelper;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.exception.login.*;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.repository.QuestionsRepository;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final QuestionsRepository questionsRepository;

    private final UserMapper userMapper;

    private final PasswordConfigurerCodeService configurerCodeService;

    private final AuthenticationManager authenticationManager;

    private final JWTTokenHelper tokenHelper;

    private final PasswordEncoder passwordEncoder;

    public UserSessionDto registerUser(UserRegistrationDto registrationDto) {
        User user = userMapper.userRegistrationDtoToUser(registrationDto);
        user.setPassword(encodePassword(user.getPassword()));
        return userMapper.userToSessionDto(userRepository.save(user));
    }

    public String loginUser(UserLoginDto loginDto) {
        // введенный пароль проверяется в DaoAuthenticationProvider.additionalAuthenticationChecks(...)
        try {
            Authentication authenticationRaw = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationRaw); // here check password in user
//                                                              ↓
            // Шаг 3: AuthenticationManager вызывает DaoAuthenticationProvider
            // Шаг 4: DaoAuthenticationProvider вызывает UserDetailsService.loadUserByUsername()
            // Шаг 5: Получаем UserDetails с захэшированным паролем из БД
            // Шаг 6: Сравниваем raw пароль с захэшированным через PasswordEncoder
            //         passwordEncoder.matches(rawPassword, hashedPasswordFromDB)

            // Шаг 7: Если пароли совпадают - аутентификация успешна
            //         Если нет - исключение BadCredentialsException

            String token = tokenHelper.generateToken(loginDto.getEmail(), loginDto.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            return token;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public Optional<UserSessionDto> validateUserBy(UserTokenDto userTokenDto) {
        try{
            // trust that token is validated
            Optional<UserDetails> userAuthAssume = tokenHelper.validateToken(userTokenDto.getToken());
            if (userAuthAssume.isEmpty()) {
                return Optional.empty();
            }
            // trust that user given from Token equals with User founded from userTokenDto.id.
            // This is necessary so that attackers do not replace someone else's token with their ID.
            User user = findUserEntityById(userTokenDto.getId());
            if(user.getEmail().equals(userAuthAssume.get().getUsername())
                    && user.getPassword().equals(userAuthAssume.get().getPassword())) {
                return Optional.of(userMapper.userToSessionDto(user));
            }
        }catch (UserNotFound e) {
            log.warn("user not found by id: {}", userTokenDto.getId());
        }
        return Optional.empty();
    }

    public ResponseEntity<UserSessionDto> validateUserBy(UserTokenDto userTokenDto, UserDetailsImpl authUser) {
        return null;
    }

    public void updateUser(UserUpdateDto updateDto, UserDto userDto) {
        if (!updateDto.getEmail().equals(userDto.getEmail())) {
            userRepository.findByEmail(updateDto.getEmail())
                    .ifPresent((user) -> {
                        throw new UserAlreadyExistsException(user.getEmail());
                    });
        }

        userMapper.updateUserDtoFromUpdateDto(userDto, updateDto);
        if (Objects.equals(userDto.getPassword(), updateDto.getNewPassword())) {
            updateDto.setPassword(userDto.getPassword());
            userDto.setPassword(encodePassword(userDto.getPassword()));
        }
        userRepository.save(userMapper.userDtoToUser(userDto));
    }


    public void deleteUserByPassword(UserDto userDto, String password) {
        checkUserPassword(userDto, password);

        List<Question> listQuestion = questionsRepository.findAllByForUser(findUserEntityByEmail(userDto.getEmail()));
        // when will be commit transaction, then Hibernate automatically update records in DB, therefore, you do not need to explicitly delete
        listQuestion.forEach(quest -> {
            quest.setForUser(null);
            quest.setAnswerText("");
        });

        User user = findUserEntityByEmail(userDto.getEmail());
        userRepository.delete(user);
    }

    public String resetPasswordAndGenerateConfigurerCodeVerify(String email) {
        PasswordConfigurerCodeDto passwordConfigurerCode = configurerCodeService
                .createConfigurerCode(findUserEntityByEmail(email));
        return passwordConfigurerCode.getCode();
    }

    public void changePassword(UserConfigurationCodeDto userConfCodeDto) {
        PasswordConfigurerCode configCode = configurerCodeService
                .findConfigurerCodeByCodeAndUserEmail(userConfCodeDto.getCode(), userConfCodeDto.getEmail());
        User user = configCode.getUser();
        user.setPassword(passwordEncoder.encode(userConfCodeDto.getNewPassword()));
        userRepository.save(user); // update password
        configurerCodeService.deleteById(configCode.getId());
    }

    public void checkUserPassword(UserDto userDto, String matchPassword) {
        if (!matchesPassword(matchPassword, userDto.getPassword())) {
            throw new InvalidedUserPasswordException();
        }
    }

    public UserSessionDto getUserSessionByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToSessionDto)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public User findUserEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFound::new);
    }

    public UserDto findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public User findUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public boolean isUpdatedEmailOrPassword(UserDto userTarget, UserUpdateDto updateDto) {
        return !updateDto.getEmail().equals(userTarget.getEmail()) ||
               (!updateDto.getNewPassword().isEmpty() &&
                !matchesPassword(updateDto.getNewPassword(), userTarget.getPassword()));
    }

    private UserDto findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(entity -> matchesPassword(password, entity.getPassword()))
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new LoginException(email, password));
    }

    private String encodePassword(String regularPassword) {
        return passwordEncoder.encode(regularPassword);
    }

    private boolean matchesPassword(String regularPassword, String encodedPassword) {
        return passwordEncoder.matches(regularPassword, encodedPassword);
    }

    public List<String> receiveListEmailsExceptAuth(UserDto userDto) {
        List<User> users = userRepository.findAllByEmailNot(userDto.getEmail());
        return users.stream().map(User::getEmail).toList();
    }
}
