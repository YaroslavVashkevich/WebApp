package org.example.dto;

import lombok.*;

@Value
@Builder
public class FlightDto {
    private final Long id;
    private final String description;
}
