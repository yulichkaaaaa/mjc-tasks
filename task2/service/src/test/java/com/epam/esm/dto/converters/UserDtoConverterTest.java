package com.epam.esm.dto.converters;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDtoConverterTest {

    private UserDtoConverter userDtoConverter;
    private User user;
    private UserDto userDto;

    @BeforeAll
    public void init() {
        userDtoConverter = new UserDtoConverter();
        initUserData();
    }

    private void initUserData() {
        user = new User(1, "yulia", "yulia@gmail.com", "12345678");
        userDto = new UserDto(1, "yulia", "yulia@gmail.com", "12345678");
    }

    @Test
    public void convertToDtoTest() {
        Assertions.assertEquals(userDto, userDtoConverter.convertToDto(user));
    }

    @Test
    public void convertToEntityTest() {
        Assertions.assertEquals(user, userDtoConverter.convertToEntity(userDto));
    }
}
