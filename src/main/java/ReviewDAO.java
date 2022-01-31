import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ReviewDAO {
    public final Connection connection;

    public ReviewDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS review (" +
                    "review_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "content VARCHAR(1000), " +
                    "owner_id INTEGER)");
        }
    }

    public void insert(Review review) throws SQLException {
        if (review.review_id != 0) {
            throw new IllegalArgumentException("ID is: " + review.review_id);
        }

        final String sql = "INSERT INTO review(content, owner_id) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, review.content);
            statement.setInt(2, review.owner_id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating review failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.review_id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating review failed, no ID obtained");
                }
            }
        }
    }

    public Collection<Review> getAll() throws SQLException {
        Collection<Review> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM review");
            while (cursor.next()) {
                books.add(createReviewFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public Collection<Review> getReviewsByUserID(int user_id) throws SQLException {
        Collection<Review> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM review WHERE owner_id = ?", user_id)) {
            statement.setInt(1, user_id);
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                books.add(createReviewFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public Collection<Review> findByContent(String content) throws SQLException {
        Collection<Review> reviews = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT review.* FROM review " +
                        "JOIN user ON review.owner_id = user.user_id " +
                        "WHERE review.content LIKE ?")) {
            statement.setString(1, "%" + content + "%");
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                reviews.add(createReviewFromCursorIfPossible(cursor));
            }
        }
        return reviews;
    }

    public void update(Review review) throws SQLException {
        if (review.review_id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }

        String sql = "UPDATE review SET content = ? WHERE owner_id = ? AND review_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, review.content);
            statement.setInt(2, review.owner_id);
            statement.setInt(3, review.review_id);

            statement.executeUpdate();
        }
    }

    public void deleteByContent(String content) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("DELETE FROM review WHERE content = %%%s%%", content));
            cursor.close();
        }
    }

    public void deleteByID(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("DELETE FROM review WHERE user_id = %%%d%%", id));
            cursor.close();
        }
    }

    private Review createReviewFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Review review = new Review();
        review.review_id = cursor.getInt("review_id");
        review.content = cursor.getString("content");
        review.owner_id = cursor.getInt("owner_id");
        return review;
    }
}
