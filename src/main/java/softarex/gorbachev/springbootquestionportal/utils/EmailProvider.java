package softarex.gorbachev.springbootquestionportal.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

@AllArgsConstructor
@NoArgsConstructor
public abstract class EmailProvider {

    @Getter
    @Setter
    protected JavaMailSender mailSender;

    @Getter
    @Setter
    protected String message;

    @Getter
    @Setter
    protected String mailSubject;

    public abstract Object buildRegistrationMail(String emailTo, String password, String username);

    public abstract Object buildDeletingMail(String emailTo, String username);

    public abstract Object buildResetUserPasswordMail(String emailTo, String confirmationCode);


    @Value("${spring.mail.username}")
    protected String emailFrom;

    @Value("${email.msg.registration.regexp}")
    protected String MSG_REGISTERED;

    @Value("${email.msg.deleted.regexp}")
    protected String MSG_DELETED;

    @Value("${email.msg.reset.regexp}")
    protected String MSG_RESET;

    @Value("${email.subj.registration}")
    protected String SUBJ_REGISTERED;

    @Value("${email.subj.deleted}")
    protected String SUBJ_DELETED;

    @Value("${email.subj.reset}")
    protected String SUBJ_RESET;
}
