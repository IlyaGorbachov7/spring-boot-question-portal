package softarex.gorbachev.springbootquestionportal.utils.aop.ann;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterSendTo {
    String way() default "";
}
