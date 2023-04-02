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
        senderProvider.sendEmailRegistration(emailTo, "skf#2323", "Ilya Gorbacvev");
    }
}
