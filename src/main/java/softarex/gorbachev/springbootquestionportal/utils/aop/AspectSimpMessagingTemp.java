package softarex.gorbachev.springbootquestionportal.utils.aop;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.config.security.UserDetailsImpl;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;
import softarex.gorbachev.springbootquestionportal.service.mdls.EmailResponse;
import softarex.gorbachev.springbootquestionportal.utils.aop.ann.AfterSendSubscribersTo;
import softarex.gorbachev.springbootquestionportal.utils.aop.ann.AfterSendTo;

import java.util.Arrays;
import java.util.Objects;

@Component
@AllArgsConstructor
@Aspect
public class AspectSimpMessagingTemp {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @AfterReturning(value = "within(softarex.gorbachev.springbootquestionportal.controller.*) && " +
                            "@target(annOnMethod) || @annotation(annOnMethod)")
    public void afterAdvice(JoinPoint jp, AfterSendTo annOnMethod) {
        annOnMethod = Objects.requireNonNullElse(annOnMethod, jp.getTarget().getClass().getAnnotation(AfterSendTo.class));
        simpMessagingTemplate.convertAndSend(annOnMethod.way(), "");
    }

    @AfterReturning(value = "within(softarex.gorbachev.springbootquestionportal.controller.*) && args(..,auth) &&" +
                            "(@target(annOnMethod) || @annotation(annOnMethod))")
    public void afterAdviceSubscriber(JoinPoint jp, AfterSendSubscribersTo annOnMethod, UserDetailsImpl auth) {
        annOnMethod = Objects.requireNonNullElse(annOnMethod, jp.getTarget().getClass().getAnnotation(AfterSendSubscribersTo.class));
        String url = annOnMethod.way().replace(String.format("{%s}", annOnMethod.pathUnique()), auth.getTarget().getEmail());
        Arrays.stream(jp.getArgs()).filter(arg -> arg instanceof UserUpdateDto)
                .map(arg -> (UserUpdateDto) arg)
                .findFirst()
                .ifPresentOrElse((updateDto -> {
                    simpMessagingTemplate.convertAndSend(url, EmailResponse.of(auth.getTarget().getEmail(), updateDto.getEmail()));
                }), () -> {
                    simpMessagingTemplate.convertAndSend(url, EmailResponse.of(auth.getTarget().getEmail(), auth.getTarget().getEmail()));
                });
    }
}
