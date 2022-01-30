import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class LibraryMain {
    Statement statement = null;

    public static void main(String[] args) {
        new LibraryMain().run();
    }

    private void run() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:library.db")) {
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
        ILibraryRepository libraryRepository = initializeRepository(connection);

        Author author1 = new Author("Толкин", 1900);
        Author author2 = new Author("Толкин", 1900);
        libraryRepository.saveBook(new Book("Властелин колец: 1"), author1);
        libraryRepository.saveBook(new Book("Властелин колец: 2"), author1);
        libraryRepository.saveBook(new Book("C++"), author2);

        Collection<Book> books = libraryRepository.findBooksByAuthorName("и");
        System.out.println(books);
    }

    private ILibraryRepository initializeRepository(Connection connection) throws SQLException {
        BookDAO bookDAO = new BookDAO(connection);
        AuthorDAO authorDAO = new AuthorDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        ReviewDAO reviewDAO = new ReviewDAO(connection);

        authorDAO.createTable();
        bookDAO.createTable();
        userDAO.createTable();
        reviewDAO.createTable();

        return new SqlLibraryRepository(bookDAO, authorDAO, userDAO, reviewDAO);
    }
}
