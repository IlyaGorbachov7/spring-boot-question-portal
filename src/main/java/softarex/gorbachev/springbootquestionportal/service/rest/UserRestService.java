package softarex.gorbachev.springbootquestionportal.service.rest;

import org.springframework.http.ResponseEntity;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;

import java.util.List;

public interface UserRestService {
    ResponseEntity<MessageResponse> register(UserRegistrationDto registrationDto);

    ResponseEntity<MessageLoginResponse> login(UserLoginDto loginDto);

    UserSessionDto currentSessionUser(UserDetailsImpl sessionDetails);

    ResponseEntity<MessageLoginResponse> updateSessionUser(UserUpdateDto updateDto, UserDetailsImpl authUser);

    ResponseEntity<MessageResponse> deleteSessionUser(UserPasswordDto passwordDto, UserDetailsImpl authUser);

    ResponseEntity<MessageResponse> resetPasswordFor(UserEmailDto emailDto);

    ResponseEntity<MessageResponse> changePassword(UserConfigurationCodeDto configurationCodeDto);

    ResponseEntity<List<String>> receiveEmailsAbsentUsersExceptAuth(UserDetailsImpl authUser);
}