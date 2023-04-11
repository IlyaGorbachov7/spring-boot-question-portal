package softarex.gorbachev.springbootquestionportal.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.PasswordConfigurerCodeDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-11T19:20:26+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class PasswordConfigurationCodeMapperImpl extends PasswordConfigurationCodeMapper {

    @Override
    public PasswordConfigurerCode toPasswordConfigurerCode(User user, String configurationCode, LocalDateTime dateTime) {
        if ( user == null && configurationCode == null && dateTime == null ) {
            return null;
        }

        PasswordConfigurerCode passwordConfigurerCode = new PasswordConfigurerCode();

        passwordConfigurerCode.setUser( user );
        passwordConfigurerCode.setCode( configurationCode );
        passwordConfigurerCode.setExpiryDateTime( dateTime );

        return passwordConfigurerCode;
    }

    @Override
    public PasswordConfigurerCodeDto passwordConfCodeToPasswordConfCodeDto(PasswordConfigurerCode passwordConfigurerCode) {
        if ( passwordConfigurerCode == null ) {
            return null;
        }

        PasswordConfigurerCodeDto passwordConfigurerCodeDto = new PasswordConfigurerCodeDto();

        passwordConfigurerCodeDto.setId( passwordConfigurerCode.getId() );
        passwordConfigurerCodeDto.setCode( passwordConfigurerCode.getCode() );
        passwordConfigurerCodeDto.setExpiryDateTime( passwordConfigurerCode.getExpiryDateTime() );

        return passwordConfigurerCodeDto;
    }

    @Override
    public PasswordConfigurerCode passwordConfCodeDtoToPasswordConfCode(PasswordConfigurerCodeDto passwordConfigurerCodeDto) {
        if ( passwordConfigurerCodeDto == null ) {
            return null;
        }

        PasswordConfigurerCode passwordConfigurerCode = new PasswordConfigurerCode();

        passwordConfigurerCode.setId( passwordConfigurerCodeDto.getId() );
        passwordConfigurerCode.setCode( passwordConfigurerCodeDto.getCode() );
        passwordConfigurerCode.setExpiryDateTime( passwordConfigurerCodeDto.getExpiryDateTime() );

        return passwordConfigurerCode;
    }
}
