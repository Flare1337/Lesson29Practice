import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDAO {
    public final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_name VARCHAR(100))");
        }
    }

    public void insert(User user) throws SQLException {
        if (user.user_id != 0) {
            throw new IllegalArgumentException("ID is: " + user.user_id);
        }

        final String sql = "INSERT INTO user(user_name) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.name);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.user_id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained");
                }
            }
        }
    }

    public Collection<User> getAll() throws SQLException {
        Collection<User> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM user");
            while (cursor.next()) {
                books.add(createUserFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public User getUserByUserID(int user_id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user WHERE user_id = ?", user_id)) {
            statement.setInt(1, user_id);
            ResultSet cursor = statement.executeQuery();
            if (cursor.next()) {
                return createUserFromCursorIfPossible(cursor);
            }
            throw new IllegalArgumentException("User is not found");
        }
    }

    public Collection<User> findByName(String name) throws SQLException {
        Collection<User> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT user.* FROM user " +
                        "JOIN review ON user.user_id = review.owner_id " +
                        "WHERE user.user_name LIKE ?")) {
            statement.setString(1, "%" + name + "%");
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                books.add(createUserFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public void update(User user) throws SQLException {
        if (user.user_id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }

        String sql = "UPDATE user SET user_name = ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.name);
            statement.setInt(2, user.user_id);

            statement.executeUpdate();
        }
    }

    public void deleteByName(String name) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("DELETE FROM user WHERE user_name = %%%s%%", name));
            cursor.close();
        }
    }

    public void deleteByID(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("DELETE FROM user WHERE user_id = %%%d%%", id));
            cursor.close();
        }
    }

    private User createUserFromCursorIfPossible(ResultSet cursor) throws SQLException {
        User user = new User();
        user.user_id = cursor.getInt("user_id");
        user.name = cursor.getString("user_name");
        return user;
    }
}
