package softarex.gorbachev.springbootquestionportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import softarex.gorbachev.springbootquestionportal.constant.CommonAppConstant;

@Configuration(proxyBeanMethods = false)
@CrossOrigin(CommonAppConstant.CROSS_ORIGIN_LOCALHOST3000)
@PropertySource(CommonAppConstant.PROPERTY_FILE_MESSAGEEMAIL)
public class SpringAppConfig {
}
