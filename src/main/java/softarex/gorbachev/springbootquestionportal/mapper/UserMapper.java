package softarex.gorbachev.springbootquestionportal.mapper;


import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserRegistrationDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserSessionDto;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    public abstract User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);

    public abstract UserSessionDto userToSessionDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "updateDto.newPassword",
            conditionExpression = "java(updateDto.getNewPassword() != null && !updateDto.getNewPassword().isEmpty())",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserDtoToUser(UserUpdateDto updateDto, @MappingTarget User user);

    /**
     * strong setting newPassword from UserUpdateDto to UserLoginDto
     */
    @Mapping(target = "password", source = "updateDto.newPassword")
    public abstract UserLoginDto userUpdateDtoToUserLoginDtoWithNewPassword(UserUpdateDto updateDto);

    public abstract UserLoginDto userUpdateDtoToUserLoginDtoPreviousPassword(UserUpdateDto updateDto);


    public UserLoginDto receiveUpdatedUserLoginDto(UserUpdateDto updateDto) {
        if (!updateDto.getNewPassword().isEmpty()) {
            return userUpdateDtoToUserLoginDtoWithNewPassword(updateDto);
        } else {
            return userUpdateDtoToUserLoginDtoPreviousPassword(updateDto);
        }
    }
}
