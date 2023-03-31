package softarex.gorbachev.springbootquestionportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import softarex.gorbachev.springbootquestionportal.config.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.model.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.model.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.UserRestService;

import java.time.LocalDateTime;

@Controller
@ResponseBody // REST controller
@RequestMapping(value = "/api/v1/users")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserRestService userRestService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registration(UserRegistrationDto registrationDto) {
        return userRestService.register(registrationDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageLoginResponse> login(UserLoginDto loginDto) {
        return userRestService.login(loginDto);
    }

    @PostMapping("/reset")
    public ResponseEntity<MessageResponse> resetPassword(UserEmailDto emailDto) {
        return userRestService.resetPasswordFor(emailDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(UserConfigurationCodeDto configurationCodeDto) {
        return userRestService.changePassword(configurationCodeDto);
    }

    @GetMapping("/cur-user")
    public ResponseEntity<UserSessionDto> currentSessionUser(@AuthenticationPrincipal() UserDetailsImpl authUser) {
        UserSessionDto sessionDto = userRestService.currentSessionUser(authUser);
        return new ResponseEntity<>(sessionDto, HttpStatus.IM_USED);
    }

    @PutMapping("/")
    public ResponseEntity<MessageLoginResponse> updateSessionUser(UserUpdateDto updateDto,
                                                                  @AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestService.updateSessionUser(updateDto, authUser);
    }

    @DeleteMapping("/")
    public ResponseEntity<MessageResponse> deleteSessionUser(UserPasswordDto passwordDto,
                                                             @AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestService.deleteSessionUserByPassword(passwordDto, authUser);
    }


    @GetMapping("/")
    public String getMsg() {
        return "Kello by framnd";
    }


}




