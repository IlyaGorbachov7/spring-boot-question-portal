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
import softarex.gorbachev.springbootquestionportal.config.JWTTokenHelper;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserRegistrationDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserSessionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;
import softarex.gorbachev.springbootquestionportal.exception.login.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.login.InvalidedUserPasswordException;
import softarex.gorbachev.springbootquestionportal.exception.login.LoginException;
import softarex.gorbachev.springbootquestionportal.exception.login.UserAlreadyExistsException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JWTTokenHelper tokenHelper;

    private final PasswordEncoder passwordEncoder;


    public UserSessionDto getUserSessionByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToSessionDto)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

    public UserSessionDto registrateUser(UserRegistrationDto registrationDto) {
        User user = userMapper.userRegistrationDtoToUser(registrationDto);
        user.setPassword(encodePassword(user.getPassword()));
        return userMapper.userToSessionDto(userRepository.save(user));
    }

    public String loginUser(UserLoginDto loginDto) {
        User user = findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
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

    public User updateUser(UserUpdateDto updateDto, User userTarget) {
        if (!updateDto.getEmail().equals(userTarget.getEmail())) {
            userRepository.findByEmail(updateDto.getEmail()).ifPresent((user) -> {
                System.out.println("User already exist " + user);
                throw new UserAlreadyExistsException(updateDto.getEmail());
            });
        }
        userMapper.updateUserDtoToUser(updateDto, userTarget); // changed password is newPassword not Empty
        if (Objects.equals(userTarget.getPassword(), updateDto.getNewPassword())) { // is password is changed
            userTarget.setPassword(encodePassword(userTarget.getPassword())); // encode password
        }
        userRepository.save(userTarget);// then just update entity
        return userTarget;
    }


    public void checkUserPassword(User user, String matchPassword) {
        if (!matchesPassword(matchPassword, user.getPassword())) {
            throw new InvalidedUserPasswordException();
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(entity -> matchesPassword(password, entity.getPassword()))
                .orElseThrow(() -> new LoginException(email, password));
    }

    private String encodePassword(String regularPassword) {
        return passwordEncoder.encode(regularPassword);
    }

    private boolean matchesPassword(String regularPassword, String encodedPassword) {
        return passwordEncoder.matches(regularPassword, encodedPassword);
    }

    public boolean isUpdatedEmailOrPassword(User userTarget, UserUpdateDto updateDto) {
        return !updateDto.getEmail().equals(userTarget.getEmail()) ||
               (!updateDto.getNewPassword().isEmpty() &&
                !matchesPassword(updateDto.getNewPassword(), userTarget.getPassword()));
    }
}
