package org.example.dto;

import lombok.Builder;
import lombok.Value;
import org.example.entity.Gender;
import org.example.entity.Role;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    LocalDate birthday;
    String email;
    String image;
    Role role;
    Gender gender;}