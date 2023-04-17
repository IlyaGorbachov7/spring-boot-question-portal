package softarex.gorbachev.springbootquestionportal.utils;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;

@AllArgsConstructor
public class SimpleMessageEmailProvider extends EmailProvider {

    @Override
    public synchronized SimpleMailMessage buildRegistrationMail(String emailTo, String password, String username) {
        mailSubject = SUBJ_REGISTERED;
        message = MSG_REGISTERED.replace("{1}", emailTo).replace("{2}", password);
        return setParamMailMessage(emailTo);
    }

    @Override
    public synchronized SimpleMailMessage buildDeletingMail(String emailTo, String username) {
        mailSubject = SUBJ_DELETED;
        message = MSG_DELETED.replace("{1}", emailTo);
        return setParamMailMessage(emailTo);
    }

    @Override
    public synchronized SimpleMailMessage buildResetUserPasswordMail(String emailTo, String confirmationCode) {
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
}
