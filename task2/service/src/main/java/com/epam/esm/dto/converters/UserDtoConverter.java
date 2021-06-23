package com.epam.esm.dto.converters;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto convertToDto(User user){
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash());
    }

    public User convertToEntity(UserDto userDto){
        return new User(userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword());
    }
}
