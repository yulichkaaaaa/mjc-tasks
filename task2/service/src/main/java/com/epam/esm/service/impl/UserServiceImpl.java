package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@code UserService}.
 *
 * @author Shuleiko Yulia
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;
    private OrderDtoConverter orderDtoConverter;
    private GiftCertificateDtoConverter giftCertificateDtoConverter;
    private TagDtoConverter tagDtoConverter;

    /**
     * Construct user service with all necessary dependencies.
     */
    public UserServiceImpl(UserRepository userRepository,
                           UserDtoConverter userDtoConverter,
                           OrderDtoConverter orderDtoConverter,
                           GiftCertificateDtoConverter giftCertificateDtoConverter,
                           TagDtoConverter tagDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.orderDtoConverter = orderDtoConverter;
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto findUserById(long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(userId);
        }
        return userDtoConverter.convertToDto(userOptional.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(user -> userDtoConverter.convertToDto(user))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderDto> findAllUserOrders(long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(userId);
        }
        return userRepository.findAllUserOrders(userId)
                .stream()
                .map(order -> {
                    UserDto userDto = userDtoConverter.convertToDto(order.getUser());
                    GiftCertificateDto giftCertificateDto =
                            giftCertificateDtoConverter.convertToDto(order.getGiftCertificate(),
                                    order.getGiftCertificate().getTags()
                                            .stream()
                                            .map(tag -> tagDtoConverter.convertToDto(tag))
                                            .collect(Collectors.toSet()));
                    return orderDtoConverter.convertToDto(order, userDto, giftCertificateDto);
                }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDto findUserMostPopularTag(long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(userId);
        }
        Optional<Tag> tagOptional = userRepository.findUserMostPopularTag(userId);
        return tagOptional.map(tag -> tagDtoConverter.convertToDto(tag)).orElse(null);
    }
}
