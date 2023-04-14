package softarex.gorbachev.springbootquestionportal.utils.aop;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.utils.aop.ann.AfterSendTo;

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
}
