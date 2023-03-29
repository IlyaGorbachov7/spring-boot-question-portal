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
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.config.JWTTokenHelper;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserRegistrationDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserSessionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;
import softarex.gorbachev.springbootquestionportal.exception.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.LoginException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.repository.UserRepository;

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
        registrationDto.setPassword(registrationDto.getPassword());
        User user = userMapper.userRegistrationDtoToUser(registrationDto);
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

    @Transactional
    public UserSessionDto updateUser(UserUpdateDto updateDto, User userTarget) {
        userMapper.updateUserDtoToUser(updateDto, userTarget);
        userRepository.save(userTarget);
        return userMapper.userToSessionDto(userTarget);
    }
//----------------------------------------------------------------------------------------------------------------------

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(entity -> matchesPassword(password, entity.getPassword()))
                .orElseThrow(() -> new LoginException(password));
    }

    private boolean matchesPassword(String regularPassword, String encodedPassword) {
        return passwordEncoder.matches(regularPassword, encodedPassword);
    }
}
