import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class MainDAO {
    Statement statement = null;

    public static void main(String[] args) {
        new MainDAO().run();
    }

    private void run() {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
                if (statement == null) {
                    statement = connection.createStatement();
                }
                doSqlTasks(connection);
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public void doSqlTasks(Connection connection) throws SQLException {
        AuthorDAO authorDAO = new AuthorDAO(connection);
        authorDAO.createTable();
        BookDAO bookDAO = new BookDAO(connection);
        bookDAO.createTable();

        Collection<Author> authors = authorDAO.getAll();
        if (authors.isEmpty()) {
            Author author1 = new Author("Толкин", 1900);
            Author author2 = new Author("Стратоуструп", 1900);
            authorDAO.insert(author1);
            System.out.println("author1: " + author1);
            authorDAO.insert(author2);
            System.out.println("author2: " + author1);

            authors.add(author1);
            authors.add(author2);
        }

        Author firstAuthor = authors.stream().findFirst().orElseThrow(RuntimeException::new);

        Book book = new Book();
        book.authorID = firstAuthor.id;

        bookDAO.insert(book);
        System.out.println("book: " + book);
    }
}
