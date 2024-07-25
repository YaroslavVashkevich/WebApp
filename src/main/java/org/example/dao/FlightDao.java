package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Flight;
import org.example.entity.FlightStatus;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class FlightDao implements Dao<Long, Flight> {

    private static final FlightDao INSTANCE = new FlightDao();

    private static final String FIND_ALL = """
            SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status
            FROM flight          
            """;

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Flight save(Flight entity) {
        return null;
    }

    @Override
    public void update(Flight entity) {

    }

    @Override
    public Optional<Flight> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Flight> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(buildFlight(resultSet));
            }
            return flights;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Flight buildFlight(ResultSet resultSet) throws SQLException {
        return Flight.builder()
                .id(resultSet.getObject("id", Long.class))
                .flightNo(resultSet.getObject("flight_no", String.class))
                .departureDate(resultSet.getObject("departure_date", Timestamp.class).toLocalDateTime())
                .departureAirportCode(resultSet.getObject("departure_airport_code", String.class))
                .arrivalDate(resultSet.getObject("arrival_date", Timestamp.class).toLocalDateTime())
                .arrivalAirportCode(resultSet.getObject("arrival_airport_code", String.class))
                .aircraftId(resultSet.getObject("aircraft_id", Integer.class))
                .status(FlightStatus.valueOf(resultSet.getObject("status", String.class))).build();
    }
}
