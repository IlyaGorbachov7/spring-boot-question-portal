package softarex.gorbachev.springbootquestionportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:emailmessage.properties")
public class SpringBootQuestionPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuestionPortalApplication.class, args);

    }

}
