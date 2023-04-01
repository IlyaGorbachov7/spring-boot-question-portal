package softarex.gorbachev.springbootquestionportal.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserRegistrationDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserSessionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-31T21:27:53+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

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
    public void updateUserDtoToUser(UserUpdateDto updateDto, User user) {
        if ( updateDto == null ) {
            return;
        }

        if ( updateDto.getNewPassword() != null && !updateDto.getNewPassword().isEmpty() ) {
            user.setPassword( updateDto.getNewPassword() );
        }
        user.setFirstName( updateDto.getFirstName() );
        user.setLastName( updateDto.getLastName() );
        user.setEmail( updateDto.getEmail() );
        user.setPhone( updateDto.getPhone() );
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
