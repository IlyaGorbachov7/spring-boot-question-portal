package softarex.gorbachev.springbootquestionportal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import softarex.gorbachev.springbootquestionportal.utils.EmailSenderProvider;

@ActiveProfiles("test")
@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TestEmailSend {

    @Autowired
    EmailSenderProvider senderProvider;

    String emailTo = "gorbacevaili891@gmail.com";

    @Test
    void test1() {
        senderProvider.sendEmailRegistration(emailTo, "@bkmz829323","Ilya Gorbacvev");
    }

    @Test
    void test2() {
        senderProvider.sendEmailDelete(emailTo, "Ilya Gorbacvev");
    }

    @Test
    void test3() {
        senderProvider.sendEmailConfirmationCode(emailTo,"323fds2#3f");
    }



}
