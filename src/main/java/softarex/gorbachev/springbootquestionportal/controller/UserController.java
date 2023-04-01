package softarex.gorbachev.springbootquestionportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.UserRestService;
import softarex.gorbachev.springbootquestionportal.service.rest.impl.UserRestServiceImpl;

import static softarex.gorbachev.springbootquestionportal.constant.requ_map.UsersRequestMappingConst.*;

@RestController
@RequestMapping(USERS_CONTROLLER)
@RequiredArgsConstructor
public class UserController {

    private final UserRestService userRestServiceImpl;

    @PostMapping(USERS_REGISTER)
    public ResponseEntity<MessageResponse> registration(@RequestBody UserRegistrationDto registrationDto) {
        return userRestServiceImpl.register(registrationDto);
    }

    @PostMapping(USERS_LOGIN)
    public ResponseEntity<MessageLoginResponse> login(@RequestBody UserLoginDto loginDto) {
        return userRestServiceImpl.login(loginDto);
    }

    @PostMapping(USERS_RESETPASSWORD)
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody UserEmailDto emailDto) {
        return userRestServiceImpl.resetPasswordFor(emailDto);
    }

    @PostMapping(USERS_CHANGEPASSWORD)
    public ResponseEntity<MessageResponse> changePassword(@RequestBody UserConfigurationCodeDto configurationCodeDto) {
        return userRestServiceImpl.changePassword(configurationCodeDto);
    }

    @GetMapping(USERS_CURUSER)
    public ResponseEntity<UserSessionDto> currentSessionUser(@AuthenticationPrincipal() UserDetailsImpl authUser) {
        UserSessionDto sessionDto = userRestServiceImpl.currentSessionUser(authUser);
        return new ResponseEntity<>(sessionDto, HttpStatus.IM_USED);
    }

    @PutMapping(USERS_CURUSER)
    public ResponseEntity<MessageLoginResponse> updateSessionUser(@RequestBody UserUpdateDto updateDto,
                                                                  @AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestServiceImpl.updateSessionUser(updateDto, authUser);
    }

    @DeleteMapping(USERS_CURUSER)
    public ResponseEntity<MessageResponse> deleteSessionUser(@RequestBody UserPasswordDto passwordDto,
                                                             @AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestServiceImpl.deleteSessionUser(passwordDto, authUser);
    }

    @GetMapping("/")
    public String getMsg() {
        return "Kello by framnd";
    }


}




