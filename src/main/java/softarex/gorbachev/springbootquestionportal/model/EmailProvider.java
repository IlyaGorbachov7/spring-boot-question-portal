package softarex.gorbachev.springbootquestionportal.model;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailProvider {

    private String message;

    private String mailSubject;

    public synchronized SimpleMailMessage buildRegistrationMailMsg(String emailTo, String password) {
        mailSubject = SUBJ_REGISTERED;
        message = MSG_REGISTERED.replace("{1}", emailTo).replace("{2}", password);
        return setParamMailMessage(emailTo);
    }

    public synchronized SimpleMailMessage buildDeletingMailMsg(String emailTo) {
        mailSubject = SUBJ_DELETED;
        message = MSG_DELETED.replace("{1}", emailTo);
        return setParamMailMessage(emailTo);
    }

    public synchronized SimpleMailMessage buildResetUserPasswordMailMsg(String emailTo, String confirmationCode) {
        mailSubject = SUBJ_RESET;
        message = MSG_RESET.replace("{1}", emailTo).replace("{2}", confirmationCode);
        return setParamMailMessage(emailTo);
    }

    private SimpleMailMessage setParamMailMessage(String emailTo) {
        SimpleMailMessage sender = new SimpleMailMessage();
        sender.setFrom(emailFrom);
        sender.setTo(emailTo);
        sender.setSubject(mailSubject);
        sender.setText(message);
        return sender;
    }

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${email.msg.registration.regexp}")
    private String MSG_REGISTERED;

    @Value("${email.msg.deleted.regexp}")
    private String MSG_DELETED;

    @Value("${email.msg.reset.regexp}")
    private String MSG_RESET;

    @Value("${email.subj.registration}")
    private String SUBJ_REGISTERED;

    @Value("${email.subj.deleted}")
    private String SUBJ_DELETED;

    @Value("${email.subj.reset}")
    private String SUBJ_RESET;


}
