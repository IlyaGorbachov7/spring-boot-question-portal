package softarex.gorbachev.springbootquestionportal.service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.config.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.exception.login.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.login.UserAlreadyExistsException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.model.EmailSenderProvider;
import softarex.gorbachev.springbootquestionportal.model.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.model.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.UserService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRestService {

    private final UserService userService;

    private final UserMapper userMapper;

    private final EmailSenderProvider emailSenderProvider;

    @Transactional
    public ResponseEntity<MessageResponse> register(UserRegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        try {
            userService.getUserSessionByEmail(email);
            throw new UserAlreadyExistsException(email);
        } catch (EmailNotFoundException ex) {
            emailSenderProvider.sendEmailRegistration(email, registrationDto.getPassword());
            userService.registrateUser(registrationDto);
        }
        return new ResponseEntity<>(new MessageResponse("User is successfully registered. Mail with confirmation code is send for your email."), HttpStatus.CREATED);
    }

    public ResponseEntity<MessageLoginResponse> login(UserLoginDto loginDto) {
        String token = userService.loginUser(loginDto);
        return new ResponseEntity<>(new MessageLoginResponse("User is successfully authorized.", token),
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
            // If last update entity will be changed "password" or "email", then needed to create new JWT token
            ResponseEntity<MessageLoginResponse> res = login(userMapper.receiveUpdatedUserLoginDto(updateDto));
            emailSenderProvider.sendEmailRegistration(updateDto.getEmail(), updateDto.getPassword());

            String message = Objects.requireNonNull(res.getBody()).getMessage();
            String token = Objects.requireNonNull(res.getBody()).getToken();
            return new ResponseEntity<>(new MessageLoginResponse(String.format("Update is successful.\n%s", message), token),
                    HttpStatus.IM_USED);
        }
        userService.updateUser(updateDto, userTarget);
        return new ResponseEntity<>(new MessageLoginResponse("Update is successful.", null), HttpStatus.IM_USED);
    }

    @Transactional
    public ResponseEntity<MessageResponse> deleteSessionUserByPassword(UserPasswordDto passwordDto, UserDetailsImpl authUser) {
        userService.deleteUserByPassword(authUser.getTarget(), passwordDto.getPassword());
        emailSenderProvider.sendEmailDelete(authUser.getUsername());
        return new ResponseEntity<>(new MessageResponse("User successfully deleted"),HttpStatus.IM_USED);
    }
}
