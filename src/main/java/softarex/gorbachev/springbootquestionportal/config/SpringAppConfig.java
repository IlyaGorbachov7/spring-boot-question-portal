package softarex.gorbachev.springbootquestionportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant;
import softarex.gorbachev.springbootquestionportal.utils.EmailProvider;
import softarex.gorbachev.springbootquestionportal.utils.HtmlPageEmailProvider;
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

    @Bean
    EmailProvider htmlPageEmailProvider() {
        return new HtmlPageEmailProvider();
    }

    @Bean
    public ITemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail-templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource emailMessageSource = new ResourceBundleMessageSource();
        emailMessageSource.setBasename("mailMessages");
        return emailMessageSource;
    }

    @Bean
    public ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver, ResourceBundleMessageSource messageSource) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setTemplateEngineMessageSource(messageSource);
        return templateEngine;
    }

}
