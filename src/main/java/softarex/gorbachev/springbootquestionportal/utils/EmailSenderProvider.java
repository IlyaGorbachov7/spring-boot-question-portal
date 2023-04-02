package softarex.gorbachev.springbootquestionportal.utils;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.exception.login.InvalidedEmailException;

@Component
@AllArgsConstructor
public class EmailSenderProvider {

    private final EmailProvider messageEmailProvider;

    private final JavaMailSender mailSender;

    public void sendEmailRegistration(String emailTo, String password, String username) {
        try {
            if (messageEmailProvider instanceof SimpleMessageEmailProvider) {
                mailSender.send((SimpleMailMessage) messageEmailProvider.buildRegistrationMail(emailTo, password, username));
            } else if (messageEmailProvider instanceof HtmlPageEmailProvider) {
                mailSender.send((MimeMessage) messageEmailProvider.buildRegistrationMail(emailTo, password, username));
            } else {
                throw new IllegalArgumentException("Pleas provide some implements EmailProvider as bean object");
            }
        } catch (MailException e) {
            e.printStackTrace();
            throw new InvalidedEmailException(emailTo);
        }
    }

    public void sendEmailDelete(String emailTo, String username) {
        try {
            if (messageEmailProvider instanceof SimpleMessageEmailProvider) {
                mailSender.send((SimpleMailMessage) messageEmailProvider.buildDeletingMail(emailTo, username));
            } else if (messageEmailProvider instanceof HtmlPageEmailProvider) {
                mailSender.send((MimeMessage) messageEmailProvider.buildDeletingMail(emailTo, username));
            } else {
                throw new IllegalArgumentException("Pleas provide some implements EmailProvider as bean object");
            }
        } catch (MailException e) {
            e.printStackTrace();
            throw new InvalidedEmailException(emailTo);
        }
    }

    public void sendEmailConfirmationCode(String emailTo, String configurerCode) {
        try {
            if (messageEmailProvider instanceof SimpleMessageEmailProvider) {
                mailSender.send((SimpleMailMessage) messageEmailProvider.buildResetUserPasswordMail(emailTo, configurerCode));
            } else if (messageEmailProvider instanceof HtmlPageEmailProvider) {
                mailSender.send((MimeMessage) messageEmailProvider.buildResetUserPasswordMail(emailTo, configurerCode));
            } else {
                throw new IllegalArgumentException("Pleas provide some implements EmailProvider as bean object");
            }
        } catch (MailException e) {
            e.printStackTrace();
            throw new InvalidedEmailException(emailTo);
        }
    }
}
