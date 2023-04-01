package softarex.gorbachev.springbootquestionportal.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-31T21:27:54+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class PasswordConfigurationCodeMapperImpl extends PasswordConfigurationCodeMapper {

    @Override
    public PasswordConfigurerCode toPasswordToken(User user, String configurationCode, LocalDateTime dateTime) {
        if ( user == null && configurationCode == null && dateTime == null ) {
            return null;
        }

        PasswordConfigurerCode passwordConfigurerCode = new PasswordConfigurerCode();

        passwordConfigurerCode.setUser( user );
        passwordConfigurerCode.setCode( configurationCode );
        passwordConfigurerCode.setExpiryDateTime( dateTime );

        return passwordConfigurerCode;
    }
}
