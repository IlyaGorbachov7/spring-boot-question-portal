package softarex.gorbachev.springbootquestionportal.utils;

import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HtmlPageEmailProvider extends EmailProvider {

    private String htmlBody;

    @Autowired
    private ISpringTemplateEngine templateEngine;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public synchronized MimeMessage buildRegistrationMail(String emailTo, String password, String username) {
        mailSubject = SUBJ_REGISTERED;
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(Map.ofEntries(
                Map.entry("emailTo", emailTo),
                Map.entry("password", password),
                Map.entry("username", username)));
        htmlBody = templateEngine.process("register-tmpl.html", thymeleafContext);
        return setParamMailMessage(emailTo);
    }

    @Override
    public synchronized MimeMessage buildDeletingMail(String emailTo, String username) {
        mailSubject = SUBJ_DELETED;
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(Map.ofEntries(
                Map.entry("emailTo", emailTo)));
        htmlBody = templateEngine.process("delete-tmpl.html", thymeleafContext);
        return setParamMailMessage(emailTo);
    }

    @Override
    public synchronized MimeMessage buildResetUserPasswordMail(String emailTo, String confirmationCode) {
        mailSubject = SUBJ_RESET;
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(Map.ofEntries(
                Map.entry("emailTo", emailTo),
                Map.entry("confirmationCode", confirmationCode)));
        htmlBody = templateEngine.process("configurer-code-tmpl.html", thymeleafContext);
        return setParamMailMessage(emailTo);
    }

    @SneakyThrows
    private MimeMessage setParamMailMessage(String emailTo) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(emailTo);
        helper.setText(htmlBody, true);
        helper.setSubject(mailSubject);
        return mimeMessage;
    }
}
