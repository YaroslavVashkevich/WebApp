package org.example.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.UserDto;
import org.example.entity.User;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, UserDto>{
    private static final UserMapper INSTANCE = new UserMapper();
    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .birthday(object.getBirthday())
                .email(object.getEmail())
                .image(object.getImage())
                .role(object.getRole())
                .gender(object.getGender())
                .build();}
    public static UserMapper getInstance(){
        return INSTANCE;
    }
}
