package softarex.gorbachev.springbootquestionportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import softarex.gorbachev.springbootquestionportal.entity.PasswordConfigurerCode;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.PasswordConfigurerCodeDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserConfigurationCodeDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserDto;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class PasswordConfigurationCodeMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", source = "configurationCode")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "expiryDateTime", source = "dateTime")
    public abstract PasswordConfigurerCode toPasswordConfigurerCode(User user, String configurationCode, LocalDateTime dateTime);

    public abstract PasswordConfigurerCodeDto passwordConfCodeToPasswordConfCodeDto(PasswordConfigurerCode passwordConfigurerCode);

    public abstract PasswordConfigurerCode passwordConfCodeDtoToPasswordConfCode(PasswordConfigurerCodeDto passwordConfigurerCodeDto);

}
