package org.example.util;

import lombok.experimental.UtilityClass;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class LocalDateFormater {
    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public LocalDate format(String date){
        return LocalDate.parse(date, FORMATTER);
    }
    public boolean isValid (String date){
        try{
            return Optional.ofNullable(date).map(LocalDateFormater::format).isPresent();
        } catch (DateTimeException exception){
            return false;}}}
//    public boolean isValid (String date){ второй вариант
//        try{
//            format(date);
//            return true;
//        } catch (DateTimeException exception){
//            return false;}}



