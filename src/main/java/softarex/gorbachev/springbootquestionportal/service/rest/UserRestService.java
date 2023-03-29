package softarex.gorbachev.springbootquestionportal.service.rest;

import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.config.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserRegistrationDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserSessionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;
import softarex.gorbachev.springbootquestionportal.exception.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.UserAlreadyExistsException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.model.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.model.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.UserService;

@Service
@RequiredArgsConstructor
public class UserRestService {

    private final UserService userService;

    private final UserMapper userMapper;

    @Transactional
    public ResponseEntity<MessageResponse> register(UserRegistrationDto registrationDto) {
        try {
            userService.getUserSessionByEmail(registrationDto.getEmail());
            throw new UserAlreadyExistsException(registrationDto.getEmail());
        } catch (EmailNotFoundException ex) {
            userService.registrateUser(registrationDto);
            //TODO: отправить сообдение пользователю на imail о ригистации

            //TODO: при упешной регистации сохраняем пользователя
        }
        return new ResponseEntity<>(MessageResponse.of("User is successfully registered!"), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageLoginResponse> login(UserLoginDto loginDto) {
        String token = userService.loginUser(loginDto);
        return new ResponseEntity<>(new MessageLoginResponse("Mail with confirmation code is send for your email.", token),
                HttpStatus.IM_USED);
    }

    public UserSessionDto currentSessionUser(UserDetailsImpl sessionDetails) {
        return userService.getUserSessionByEmail(sessionDetails.getUsername());
    }

    public ResponseEntity<MessageLoginResponse> updateSessionUser(UserUpdateDto updateDto, UserDetailsImpl authUser) {
        User userTarget = authUser.getTarget();
        if (!updateDto.getEmail().equals(userTarget.getEmail())) {

        }
        UserSessionDto sessionDto = userService.updateUser(updateDto, userTarget);

        String token = " "; //:TODO logination login

        return new ResponseEntity<>(new MessageLoginResponse("Update is successful. Mail with confirmation code is send for your email.", token), HttpStatus.IM_USED);

    }
}
