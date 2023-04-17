package softarex.gorbachev.springbootquestionportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class SpringBootQuestionPortalApplication {

    public static void main(String[] args) {
    ApplicationContext context= SpringApplication.run(SpringBootQuestionPortalApplication.class, args);
        System.out.println(context);

    }

}
