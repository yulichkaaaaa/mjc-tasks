package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converters.UserDtoConverter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserDtoConverter(UserDtoConverter userDtoConverter) {
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public UserDto findUserById(long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(userId);
        }
        return userDtoConverter.convertToDto(userOptional.get());
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(user -> userDtoConverter.convertToDto(user))
                .collect(Collectors.toList());
    }
}
