package org.example.mapper;

import org.example.dto.CreateUserDto;
import org.example.entity.Gender;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.LocalDateFormater;

public class CreateUserMapper implements Mapper<CreateUserDto, User>{
    private static final String IMAGE_FOLDER = "users/";
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .birthday(LocalDateFormater.format(object.getBirthday()))
                .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName())
                .email(object.getEmail())
                .password(object.getPassword())
                .gender(Gender.valueOf(object.getGender()))
                .role(Role.valueOf(object.getRole()))
                .build();}
    public static  CreateUserMapper getInstance(){
        return INSTANCE;
    }}
