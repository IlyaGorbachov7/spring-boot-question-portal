package softarex.gorbachev.springbootquestionportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softarex.gorbachev.springbootquestionportal.config.security.JWTTokenHelper;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.Question;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.exception.login.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.login.InvalidedUserPasswordException;
import softarex.gorbachev.springbootquestionportal.exception.login.LoginException;
import softarex.gorbachev.springbootquestionportal.exception.login.UserAlreadyExistsException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.repository.QuestionsRepository;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
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
        UserDto userDto = findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            String token = tokenHelper.generateToken(loginDto.getEmail(), loginDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return token;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
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
