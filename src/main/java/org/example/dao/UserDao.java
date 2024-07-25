package org.example.dao;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.entity.Gender;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.ConnectionManager;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class UserDao implements Dao<Integer, User> {
    private static final UserDao INSTANCE = new UserDao();
    public static UserDao getInstance() {
        return INSTANCE;
    }
    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender, image)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * 
            FROM users 
            WHERE email = ? AND password =?;
            """;
    @Override
    public boolean delete(Integer id) {
        return false;
    }
    @Override
    @SneakyThrows
    public User save(User entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getBirthday());
            preparedStatement.setObject(3, entity.getEmail());
            preparedStatement.setObject(4, entity.getPassword());
            preparedStatement.setObject(5, entity.getRole().name());
            preparedStatement.setObject(6, entity.getGender().name());
            preparedStatement.setObject(7, entity.getImage());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);}}

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user =null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        }}
    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getObject("email", String.class))
                .image(resultSet.getObject("image", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(Role.find(resultSet.getObject("role", String.class))
                        .orElse(null))
                .gender(Gender.valueOf(resultSet.getObject("gender", String.class)))
                .build();
    }
    @Override
    public void update(User entity) {}

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }
    @Override
    public List<User> findAll() {
        return null;
    }}
