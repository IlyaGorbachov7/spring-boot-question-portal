package softarex.gorbachev.springbootquestionportal.service.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.exception.login.EmailNotFoundException;
import softarex.gorbachev.springbootquestionportal.exception.login.UserAlreadyExistsException;
import softarex.gorbachev.springbootquestionportal.mapper.UserMapper;
import softarex.gorbachev.springbootquestionportal.service.rest.UserRestService;
import softarex.gorbachev.springbootquestionportal.utils.EmailSenderProvider;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.UserService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRestServiceImpl implements UserRestService {

    private final UserService userService;

    private final UserMapper userMapper;

    private final EmailSenderProvider emailSenderProvider;

    @Override
    public ResponseEntity<MessageResponse> register(UserRegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        try {
            userService.findUserByEmail(email);
            throw new UserAlreadyExistsException(email);
        } catch (EmailNotFoundException ex) {
            emailSenderProvider.sendEmailRegistration(email, registrationDto.getPassword());
            userService.registerUser(registrationDto);
        }
        return new ResponseEntity<>(new MessageResponse(
                "User is successfully registered. Mail with confirmation code is send for your email."), HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<MessageLoginResponse> login(UserLoginDto loginDto) {
        String token = userService.loginUser(loginDto);
        return new ResponseEntity<>(new MessageLoginResponse("User is successfully authorized.", token),
                HttpStatus.IM_USED);
    }

    @Transactional(readOnly = true)
    @Override
    public UserSessionDto currentSessionUser(UserDetailsImpl sessionDetails) {
        return userService.getUserSessionByEmail(sessionDetails.getUsername());
    }

    @Override
    public ResponseEntity<MessageLoginResponse> updateSessionUser(UserUpdateDto updateDto, UserDetailsImpl authUser) {
        UserDto userDto = authUser.getTarget();
        // check correct entered current user password
        userService.checkUserPassword(userDto, updateDto.getPassword());

        if (userService.isUpdatedEmailOrPassword(userDto, updateDto)) {
            userService.updateUser(updateDto, userDto);
            // If last update entity will be changed "password" or "email", then needed to create new JWT token
            ResponseEntity<MessageLoginResponse> res = login(userMapper.receiveUpdatedUserLoginDto(updateDto));
            emailSenderProvider.sendEmailRegistration(updateDto.getEmail(), updateDto.getPassword());

            String message = Objects.requireNonNull(res.getBody()).getMessage();
            String token = Objects.requireNonNull(res.getBody()).getToken();
            return new ResponseEntity<>(new MessageLoginResponse(String.format("Update is successful.\n%s", message), token),
                    HttpStatus.IM_USED);
        }
        userService.updateUser(updateDto, userDto);
        return new ResponseEntity<>(new MessageLoginResponse("Update is successful.", null), HttpStatus.IM_USED);
    }

    @Override
    public ResponseEntity<MessageResponse> deleteSessionUser(UserPasswordDto passwordDto, UserDetailsImpl authUser) {
        userService.deleteUserByPassword(authUser.getTarget(), passwordDto.getPassword());
        emailSenderProvider.sendEmailDelete(authUser.getUsername());
        return new ResponseEntity<>(new MessageResponse("User successfully deleted"), HttpStatus.IM_USED);
    }

    @Override
    public ResponseEntity<MessageResponse> resetPasswordFor(UserEmailDto emailDto) {
        String emailTo = emailDto.getEmail();
        String configuredCode = userService.resetPasswordAndGenerateConfigurerCodeVerify(emailTo);
        emailSenderProvider.sendEmailConfirmationCode(emailTo, configuredCode);
        return new ResponseEntity<>(new MessageResponse("Verification code successfully send to email"),
                HttpStatus.IM_USED);
    }

    @Override
    public ResponseEntity<MessageResponse> changePassword(UserConfigurationCodeDto configurationCodeDto) {
        userService.changePassword(configurationCodeDto);
        return new ResponseEntity<>(new MessageResponse("Your password successfully changed."), HttpStatus.IM_USED);
    }
}
