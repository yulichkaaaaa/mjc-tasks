package com.epam.esm.dto.converters;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

/**
 * Converter of the user's data transfer object class.
 *
 * @author Shuleiko Yulia
 */
@Component
public class UserDtoConverter {

    /**
     * Convert user's entity to data transfer object.
     *
     * @param user the {@code User} object
     * @return the {@code UserDto} object
     */
    public UserDto convertToDto(User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash());
    }

    /**
     * Convert order's data transfer object to entity.
     *
     * @param userDto the {@code UserDto} object
     * @return the {@code User} object
     */
    public User convertToEntity(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword());
    }
}
