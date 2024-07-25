package org.example.service;

import org.example.dao.FlightDao;
import org.example.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;


public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    private FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream().map(flight -> FlightDto.builder()
                .id(flight.getId())
                .description(
                 """
                 %s - %s - %s           
                 """.formatted(flight.getDepartureAirportCode(), flight.getArrivalAirportCode(), flight.getStatus()))
                .build()).collect(Collectors.toList());
    }
}
