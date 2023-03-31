package softarex.gorbachev.springbootquestionportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import softarex.gorbachev.springbootquestionportal.constant.CommonGeneralAppConstant;

@CrossOrigin(CommonGeneralAppConstant.CROSS_ORIGIN_LOCALHOST3000)
@Configuration(proxyBeanMethods = false)
public class SpringAppConfig {
}
