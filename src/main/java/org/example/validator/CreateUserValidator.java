package org.example.validator;

import lombok.NoArgsConstructor;
import org.example.dto.CreateUserDto;
import org.example.entity.Gender;
import org.example.entity.Role;
import org.example.util.LocalDateFormater;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    @Override
    public ValidationResult isValid(CreateUserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateFormater.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));}
        if (Gender.find(object.getGender()).isEmpty()){
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));}
        if (Role.find(object.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));}
        if (object.getName().trim().equals("")){
            validationResult.add(Error.of("invalid.name", "Name is invalid"));}
        if (object.getEmail().trim().equals("")){
            validationResult.add(Error.of("invalid.email", "Email is invalid"));}
        return validationResult;}
    public static CreateUserValidator getInstance() {
        return INSTANCE;}}
