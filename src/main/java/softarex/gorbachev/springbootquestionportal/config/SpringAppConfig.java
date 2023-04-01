package softarex.gorbachev.springbootquestionportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant;
import softarex.gorbachev.springbootquestionportal.utils.PasswordGenerator;

@Configuration
@CrossOrigin(CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000)
@PropertySource(CommonAppConstant.PROPERTY_FILE_MESSAGEEMAIL)
public class SpringAppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PasswordGenerator passwordGenerator() {
        return new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }
}
