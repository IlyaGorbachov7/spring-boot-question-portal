package softarex.gorbachev.springbootquestionportal.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
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
import softarex.gorbachev.springbootquestionportal.entity.AnswerType;
import softarex.gorbachev.springbootquestionportal.repository.AnswerTypeRepository;
import softarex.gorbachev.springbootquestionportal.utils.EmailProvider;
import softarex.gorbachev.springbootquestionportal.utils.HtmlPageEmailProvider;
import softarex.gorbachev.springbootquestionportal.utils.PasswordGenerator;

@Configuration
@PropertySource(CommonAppConstant.PROPERTY_FILE_MESSAGEEMAIL)
@AllArgsConstructor
@Slf4j(topic = "SpringAppConfig")
public class SpringAppConfig {

    private final AnswerTypeRepository answerTypeRepository;

    @Bean
    InitializingBean initializingDatabasePrimaryData() {
        return () -> {
            log.info("Initializing the table {} the initial data", AnswerType.class.getSimpleName());
            try {
                answerTypeRepository.save(new AnswerType("Single line text"));
                answerTypeRepository.save(new AnswerType("Multiline text"));
                answerTypeRepository.save(new AnswerType("Date"));
                answerTypeRepository.save(new AnswerType("Radio button"));
                answerTypeRepository.save(new AnswerType("Combo box"));
                answerTypeRepository.save(new AnswerType("Check box"));
            } catch (Exception e) {
                log.info("Table already initialized");
            }

        };
    }

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
