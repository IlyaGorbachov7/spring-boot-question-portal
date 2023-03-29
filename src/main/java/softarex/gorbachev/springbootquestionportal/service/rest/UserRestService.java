package softarex.gorbachev.springbootquestionportal.service.rest;

import lombok.RequiredArgsConstructor;
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
import softarex.gorbachev.springbootquestionportal.exception.login.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.login.UserAlreadyExistsException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.model.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.model.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.UserService;

import java.util.Objects;

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
            //TODO: отправить сообдение пользователю на imail о ригистации

            //TODO: при упешной регистации сохраняем пользователя
            userService.registrateUser(registrationDto);
        }
        return new ResponseEntity<>(new MessageResponse("User is successfully registered!"), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageLoginResponse> login(UserLoginDto loginDto) {
        String token = userService.loginUser(loginDto);
        return new ResponseEntity<>(new MessageLoginResponse("Mail with confirmation code is send for your email.", token),
                HttpStatus.IM_USED);
    }

    public UserSessionDto currentSessionUser(UserDetailsImpl sessionDetails) {
        return userService.getUserSessionByEmail(sessionDetails.getUsername());
    }

    @Transactional
    public ResponseEntity<MessageLoginResponse> updateSessionUser(UserUpdateDto updateDto, UserDetailsImpl authUser) {
        User userTarget = authUser.getTarget();
        // check correct entered current user password
        userService.checkUserPassword(userTarget, updateDto.getPassword());

        if (userService.isUpdatedEmailOrPassword(userTarget, updateDto)) {
            userService.updateUser(updateDto, userTarget);

            //:TODO отправка сообщения по почте

            // If last update entity will be changed "password" or "email", then needed to create new JWT token
            ResponseEntity<MessageLoginResponse> res = login(userMapper.receiveUpdatedUserLoginDto(updateDto));
            String message = Objects.requireNonNull(res.getBody()).getMessage();
            String token = Objects.requireNonNull(res.getBody()).getToken();
            return new ResponseEntity<>(new MessageLoginResponse(String.format("Update is successful.\n%s", message), token),
                    HttpStatus.IM_USED);
        }
        userService.updateUser(updateDto, userTarget);
        return new ResponseEntity<>(new MessageLoginResponse("Update is successful.", null), HttpStatus.IM_USED);
    }

}
