package org.example.dao;

import lombok.NoArgsConstructor;
import org.example.entity.Ticket;
import org.example.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class TicketDao implements Dao<Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();

    private static final String FIND_ALL_BY_FLIGHT_ID = """
            SELECT id, passenger_no, passenger_name, flight_id, seat_no, cost
            FROM ticket
            WHERE flight_id= ?
            """;

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    public List<Ticket> findAllByFlightId(Long flightId){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_FLIGHT_ID)) {
            preparedStatement.setObject(1, flightId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return Ticket.builder()
                .id(resultSet.getObject("id", Long.class))
                .passengerNo(resultSet.getObject("passenger_no", String.class))
                .passengerName(resultSet.getObject("passenger_name", String.class))
                .flightId(resultSet.getObject("flight_id", Long.class))
                .seatNo(resultSet.getObject("seat_no",String.class))
                .cost(resultSet.getObject("cost", BigDecimal.class)).build();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Ticket save(Ticket entity) {
        return null;
    }

    @Override
    public void update(Ticket entity) {
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }
}
