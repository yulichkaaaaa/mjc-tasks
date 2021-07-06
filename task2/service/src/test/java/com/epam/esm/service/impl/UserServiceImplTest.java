package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converters.GiftCertificateDtoConverter;
import com.epam.esm.dto.converters.OrderDtoConverter;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.dto.converters.UserDtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;
    private OrderDtoConverter orderDtoConverter;
    private GiftCertificateDtoConverter giftCertificateDtoConverter;
    private TagDtoConverter tagDtoConverter;
    private UserService userService;
    private User user;
    private UserDto userDto;

    @BeforeAll
    public void init() {
        setConverters();
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository,
                userDtoConverter,
                orderDtoConverter,
                giftCertificateDtoConverter,
                tagDtoConverter);
        user = new User(1, "yulia", "yulia@gmail.com", "12345678");
        userDto = new UserDto(1, "yulia", "yulia@gmail.com", "12345678");
    }

    private void setConverters() {
        userDtoConverter = new UserDtoConverter();
        orderDtoConverter = new OrderDtoConverter();
        tagDtoConverter = new TagDtoConverter();
        giftCertificateDtoConverter = new GiftCertificateDtoConverter();
    }

    @Test
    public void findUserByIdTest() {
        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        UserDto result = userService.findUserById(user.getId());
        Assertions.assertEquals(result, userDto);
    }

    @Test
    public void findUserByIdNotFoundTest() {
        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findUserById(user.getId()));
    }

    @Test
    public void findUserOrdersTest() {
        int pageNumber = 1;
        int pageSize = 5;
        userRepository.findAllUserOrders(user.getId(), pageNumber, pageSize);
        Mockito.verify(userRepository, Mockito.times(1))
                .findAllUserOrders(1, pageNumber, pageSize);
    }

    @Test
    public void findAllUsersTest() {
        int pageNumber = 1;
        int pageSize = 5;
        userRepository.findAllUsers(pageNumber, pageSize);
        Mockito.verify(userRepository, Mockito.times(1)).findAllUsers(pageNumber, pageSize);
    }

    @Test
    public void findUserMostPopularTagTest() {
        Mockito.when(userRepository.findUserMostPopularTag(user.getId()))
                .thenReturn(Optional.of(new Tag(1, "nature")));
        Mockito.when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        TagDto actual = userService.findUserMostPopularTag(user.getId());
        TagDto expected = new TagDto(1, "nature");
        Assertions.assertEquals(actual, expected);
    }
}
