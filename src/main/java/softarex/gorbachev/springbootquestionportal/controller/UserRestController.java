package softarex.gorbachev.springbootquestionportal.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.*;
import softarex.gorbachev.springbootquestionportal.service.mdls.EmailFromForResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageLoginResponse;
import softarex.gorbachev.springbootquestionportal.service.mdls.MessageResponse;
import softarex.gorbachev.springbootquestionportal.service.rest.UserRestService;
import softarex.gorbachev.springbootquestionportal.utils.aop.ann.AfterSendSubscribersTo;
import softarex.gorbachev.springbootquestionportal.utils.aop.ann.AfterSendTo;

import java.util.List;

import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_ALL;
import static softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000;
import static softarex.gorbachev.springbootquestionportal.constant.requ_map.UsersRequestMappingConst.*;

@Validated
@RestController
@RequestMapping(USERS_CONTROLLER)
@CrossOrigin(value = {CROSS_ORIGIN_LOCALHOST3000, CROSS_ORIGIN_ALL})
// if this removed, then React don't work. Important above the controller
@AllArgsConstructor
public class UserRestController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final UserRestService userRestServiceImpl;

    @PostMapping(USERS_REGISTER)
    @AfterSendTo(way = "/public/users/crud")
    public ResponseEntity<MessageResponse> registration(@RequestBody @Valid UserRegistrationDto registrationDto) {
        return userRestServiceImpl.register(registrationDto);
    }

    @PostMapping(USERS_LOGIN)
    public ResponseEntity<MessageLoginResponse> login(@RequestBody @Valid UserLoginDto loginDto) {
        return userRestServiceImpl.login(loginDto);
    }

    @PostMapping(USERS_RESETPASSWORD)
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody @Valid UserEmailDto emailDto) {
        return userRestServiceImpl.resetPasswordFor(emailDto);
    }

    @PostMapping(USERS_CHANGEPASSWORD)
    public ResponseEntity<MessageResponse> changePassword(@RequestBody @Valid UserConfigurationCodeDto configurationCodeDto) {
        return userRestServiceImpl.changePassword(configurationCodeDto);
    }

    @GetMapping(USERS_CURUSER)
    public ResponseEntity<UserSessionDto> currentSessionUser(@AuthenticationPrincipal UserDetailsImpl authUser) {
        UserSessionDto sessionDto = userRestServiceImpl.currentSessionUser(authUser);
        return new ResponseEntity<>(sessionDto, HttpStatus.IM_USED);
    }

    @PutMapping(USERS_CURUSER)
    @AfterSendTo(way = "/public/users/crud")
    @AfterSendSubscribersTo(way = "/private/user/{from-email}", pathUnique = "from-email")
    public ResponseEntity<MessageLoginResponse> updateSessionUser(@RequestBody @Valid UserUpdateDto updateDto,
                                                                  @AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestServiceImpl.updateSessionUser(updateDto, authUser);
    }

    @DeleteMapping(USERS_CURUSER)
    @AfterSendTo(way = "/public/users/crud")
    @AfterSendSubscribersTo(way = "/private/user/{from-email}", pathUnique = "from-email")
    public ResponseEntity<MessageResponse> deleteSessionUser(@RequestBody @Valid UserPasswordDto passwordDto,
                                                             @AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestServiceImpl.deleteSessionUser(passwordDto, authUser);
    }

    @GetMapping(USERS_EMAILS)
    public ResponseEntity<List<String>> receiveEmailsAbsentUsersExceptAuth(@AuthenticationPrincipal UserDetailsImpl authUser) {
        return userRestServiceImpl.receiveEmailsAbsentUsersExceptAuth(authUser);
    }

    @MessageMapping("/private/user/subscribe-me")
    public void subscribeOnUser(@Payload EmailFromForResponse emailFromForResponse) {
        simpMessagingTemplate.convertAndSend(String.format("private/%s/subscribe-me",emailFromForResponse.getForEmail()), emailFromForResponse);
    }
}