package softarex.gorbachev.springbootquestionportal.utils;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.exception.login.InvalidedEmailException;

@Component
@AllArgsConstructor
public class EmailSenderProvider {

    private final EmailProvider provider;

    private final JavaMailSender mailSender;


    public void sendEmailRegistration(String emailTo, String password){
        try{
            mailSender.send(provider.buildRegistrationMailMsg(emailTo,password));
        }catch (Exception e){
            e.printStackTrace();
            throw new InvalidedEmailException(emailTo);
        }
    }

    public void sendEmailDelete(String emailTo){
        try{
            mailSender.send(provider.buildDeletingMailMsg(emailTo));
        }catch (Throwable e){
            throw new InvalidedEmailException(emailTo);
        }
    }

    public void sendEmailConfirmationCode(String emailTo, String configurerCode){
        try{
            mailSender.send(provider.buildResetUserPasswordMailMsg(emailTo, configurerCode));
        }catch (Throwable e){
            throw new InvalidedEmailException(emailTo);
        }
    }
}
