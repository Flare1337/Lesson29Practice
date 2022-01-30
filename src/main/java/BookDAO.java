import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookDAO {
    public final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS book (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title VARCHAR(100), " +
                    "author_ID INTEGER)");
        }
    }

    public void insert(Book book) throws SQLException {
        if (book.id != 0) {
            throw new IllegalArgumentException("ID is: " + book.id);
        }

        if (book.authorID == 0) {
            throw new IllegalArgumentException("Author ID is not set");
        }

        final String sql = "INSERT INTO book (title, author_id) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.authorID);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating book failed, no ID obtained");
                }
            }
        }
    }

    public Collection<Book> getAll() throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM book");
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public Collection<Book> getBooksByAuthorID(int author_ID) throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM author_ID WHERE id = ?", author_ID)) {
            statement.setInt(1, author_ID);
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public Collection<Book> findByTitle(String text) throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT book.* FROM book " +
                        "JOIN author ON book.author_ID = author.id " +
                        "WHERE author.name LIKE ?")) {
            statement.setString(1, "%" + text + "%");
            ResultSet cursor = statement.executeQuery();
            while (cursor.next()) {
                books.add(createBookFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public void update(Book book) throws SQLException {
        if (book.id == 0) {
            throw new IllegalArgumentException("ID is not set");
        }

        String sql = "UPDATE book SET title = ? WHERE id = ? AND author_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.id);
            statement.setInt(3, book.authorID);

            statement.executeUpdate();
        }
    }

    public void deleteByTitle(String text) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("DELETE FROM book WHERE title = %%%s%%", text));
            cursor.close();
        }
    }

    public void deleteByID(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery(
                    String.format("DELETE FROM book WHERE id = %%%d%%", id));
            cursor.close();
        }
    }

    private Book createBookFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Book book = new Book();
        book.id = cursor.getInt("id");
        book.title = cursor.getString("title");
        book.authorID = cursor.getInt("author_ID");
        return book;
    }
}
