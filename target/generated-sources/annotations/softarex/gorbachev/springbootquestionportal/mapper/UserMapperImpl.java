package softarex.gorbachev.springbootquestionportal.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserRegistrationDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserSessionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-16T20:13:16+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        if ( user.getId() != null ) {
            userDto.setId( user.getId().toString() );
        }
        userDto.setPassword( user.getPassword() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmail( user.getEmail() );
        userDto.setPhone( user.getPhone() );
        userDto.setRoles( user.getRoles() );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        if ( userDto.getId() != null ) {
            user.setId( UUID.fromString( userDto.getId() ) );
        }
        user.setPassword( userDto.getPassword() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );
        user.setEmail( userDto.getEmail() );
        user.setPhone( userDto.getPhone() );

        return user;
    }

    @Override
    public UserDto clone(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserDto userDto1 = new UserDto();

        userDto1.setId( userDto.getId() );
        userDto1.setPassword( userDto.getPassword() );
        userDto1.setFirstName( userDto.getFirstName() );
        userDto1.setLastName( userDto.getLastName() );
        userDto1.setEmail( userDto.getEmail() );
        userDto1.setPhone( userDto.getPhone() );
        userDto1.setRoles( userDto.getRoles() );

        return userDto1;
    }

    @Override
    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( userRegistrationDto.getPassword() );
        user.setFirstName( userRegistrationDto.getFirstName() );
        user.setLastName( userRegistrationDto.getLastName() );
        user.setEmail( userRegistrationDto.getEmail() );
        user.setPhone( userRegistrationDto.getPhone() );

        return user;
    }

    @Override
    public UserSessionDto userToSessionDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserSessionDto userSessionDto = new UserSessionDto();

        userSessionDto.setEmail( user.getEmail() );
        userSessionDto.setFirstName( user.getFirstName() );
        userSessionDto.setLastName( user.getLastName() );
        userSessionDto.setPhone( user.getPhone() );

        return userSessionDto;
    }

    @Override
    public void updateUserDtoFromUpdateDto(UserDto userDto, UserUpdateDto updateDto) {
        if ( updateDto == null ) {
            return;
        }

        if ( updateDto.getNewPassword() != null && !updateDto.getNewPassword().isEmpty() ) {
            userDto.setPassword( updateDto.getNewPassword() );
        }
        userDto.setFirstName( updateDto.getFirstName() );
        userDto.setLastName( updateDto.getLastName() );
        userDto.setEmail( updateDto.getEmail() );
        userDto.setPhone( updateDto.getPhone() );
    }

    @Override
    public UserLoginDto userUpdateDtoToUserLoginDtoWithNewPassword(UserUpdateDto updateDto) {
        if ( updateDto == null ) {
            return null;
        }

        UserLoginDto userLoginDto = new UserLoginDto();

        userLoginDto.setPassword( updateDto.getNewPassword() );
        userLoginDto.setEmail( updateDto.getEmail() );

        return userLoginDto;
    }

    @Override
    public UserLoginDto userUpdateDtoToUserLoginDtoPreviousPassword(UserUpdateDto updateDto) {
        if ( updateDto == null ) {
            return null;
        }

        UserLoginDto userLoginDto = new UserLoginDto();

        userLoginDto.setEmail( updateDto.getEmail() );
        userLoginDto.setPassword( updateDto.getPassword() );

        return userLoginDto;
    }
}
