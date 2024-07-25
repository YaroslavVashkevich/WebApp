package org.example.service;

import lombok.SneakyThrows;
import org.example.dao.UserDao;
import org.example.dto.CreateUserDto;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.ValidationException;
import org.example.mapper.CreateUserMapper;
import org.example.mapper.UserMapper;
import org.example.validator.CreateUserValidator;
import org.example.validator.ValidationResult;

import java.util.Optional;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private UserService() {
    }
    public Optional<UserDto> login(String email, String password){
        return userDao.findByEmailAndPassword(email, password).map(userMapper::mapFrom);
    }
    @SneakyThrows
    public Integer create(CreateUserDto userDto) {
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(userDto);
        imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream());
        userDao.save(userEntity);
        return userEntity.getId();
    }
    public static UserService getInstance() {
        return INSTANCE;
    }
}
