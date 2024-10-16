package com.tahaakocer.ybdizaynavize.mapper.user;

import com.tahaakocer.ybdizaynavize.dto.user.UserDto;
import com.tahaakocer.ybdizaynavize.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDTO);

    UserDto userToUserDto(User savedUser);
}
