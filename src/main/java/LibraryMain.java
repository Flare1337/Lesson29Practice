import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LibraryMain {
    Statement statement = null;
    static ILibraryRepository repository = null;
    static LibraryMain libraryMain = new LibraryMain();

    public static void main(String[] args) {
        try {
            Connection connection = libraryMain.connectToDB();
            libraryMain.doSqlTasks(connection);
        } catch (ConnectException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connectToDB() throws ConnectException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            if (statement == null) {
                statement = connection.createStatement();
            }
            return connection;
        }
        catch (SQLException throwables) {
            throw new ConnectException();
        }
    }

    private void closeConnectionToDB() {
        try {
            connectToDB().close();
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
        }
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

    public void invokeConsoleMenu(Author author, Book book, User user, Review review) throws SQLException {
        int input = 0;
        while (input != 5) {
            System.out.println("Choose an element to work with: \n" +
                    "1 - Author \n" +
                    "2 - Book \n" +
                    "3 - User \n" +
                    "4 - Review \n +" +
                    "5 - Exit");
            Scanner scanner = new Scanner(System.in);
            switch (input = scanner.nextInt()) {
                case 1:
                    workWithAuthor(author);
                    break;
                case 2:
                    workWithBook(book, author);
                    break;
                case 3:
                    workWithUser(user);
                    break;
                case 4:
                    workWithReview(user ,review);
                    break;
                case 5:
                    closeConnectionToDB();
                    System.out.println("Ok, i'm done...");
                    break;
                default:
                    System.out.println("What are you try'na to say?");
            }
        }
    }

    public void workWithAuthor(Author author) throws SQLException {
        int input = 0;
        while (true) {
            System.out.println("Choose a service: \n" +
                    "1 - Save author \n" +
                    "2 - Get all the authors \n" +
                    "3 - Find authors by name \n" +
                    "4 - Search authors by ID \n +" +
                    "5 - Delete author by ID \n" +
                    "6 - Delete author by name \n" +
                    "7 - Exit");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
            switch (input) {
                case 1:
                    repository.saveAuthor(author);
                    break;
                case 2:
                    repository.getAllAuthors();
                    break;
                case 3:
                    repository.findAuthorsByName(author.name);
                    break;
                case 4:
                    repository.getAuthorByID(author.id);
                    break;
                case 5:
                    repository.deleteAuthorByID(author.id);
                    break;
                case 6:
                    repository.deleteAuthorByName(author.name);
                case 7:
                    closeConnectionToDB();
                    System.out.println("Ok, i'm done...");
                    return;
                default:
                    System.out.println("What are you try'na to say?");
            }
        }
    }

    private void workWithBook(Book book, Author author) throws SQLException {
        int input = 0;
        while (true) {
            System.out.println("Choose a service: \n" +
                    "1 - Save book \n" +
                    "2 - Get all the books \n" +
                    "3 - Find books by name \n" +
                    "4 - Search books by author ID \n +" +
                    "5 - Delete book by ID \n" +
                    "6 - Delete book by title \n" +
                    "7 - Exit");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
            switch (input) {
                case 1:
                    repository.saveBook(book, author);
                    break;
                case 2:
                    repository.getAllBooks();
                    break;
                case 3:
                    repository.findBooksByAuthorName(author.name);
                    break;
                case 4:
                    repository.getBooksByAuthorID(author.id);
                    break;
                case 5:
                    repository.deleteBookByID(book.id);
                    break;
                case 6:
                    repository.deleteBookByTitle(book.title);
                case 7:
                    closeConnectionToDB();
                    System.out.println("Ok, i'm done...");
                    return;
                default:
                    System.out.println("What are you try'na to say?");
            }
        }
    }

    private void workWithUser(User user) throws SQLException {
        int input = 0;
        while (true) {
            System.out.println("Choose a service: \n" +
                    "1 - Save user \n" +
                    "2 - Get all the users \n" +
                    "3 - Find users by name \n" +
                    "4 - Search user by ID \n +" +
                    "5 - Delete user by ID \n" +
                    "6 - Delete user by name \n" +
                    "7 - Exit");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
            switch (input) {
                case 1:
                    repository.saveUser(user);
                case 2:
                    repository.getAllUsers();
                    break;
                case 3:
                    repository.findUsersByName(user.name);
                    break;
                case 4:
                    repository.getUserByID(user.user_id);
                    break;
                case 5:
                    repository.deleteUserByID(user.user_id);
                    break;
                case 6:
                    repository.deleteUserByName(user.name);
                case 7:
                    closeConnectionToDB();
                    System.out.println("Ok, i'm done...");
                    return;
                default:
                    System.out.println("What are you try'na to say?");
            }
        }
    }

    private void workWithReview(User user, Review review) throws SQLException {
        int input = 0;
        while (true) {
            System.out.println("Choose a service: \n" +
                    "1 - Save review \n" +
                    "2 - Get all the reviews \n" +
                    "3 - Find reviews by content \n" +
                    "4 - Search reviews by user ID \n +" +
                    "5 - Delete review by ID \n" +
                    "6 - Delete review by name \n" +
                    "7 - Exit");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
            switch (input) {
                case 1:
                    repository.saveReview(user, review);
                    break;
                case 2:
                    repository.getAllReviews();
                    break;
                case 3:
                    repository.findReviewsByContent(review.content);
                    break;
                case 4:
                    repository.getReviewsByUserID(user.user_id);
                    break;
                case 5:
                    repository.deleteUserByID(user.user_id);
                    break;
                case 6:
                    repository.deleteUserByName(user.name);
                case 7:
                    closeConnectionToDB();
                    System.out.println("Ok, i'm done...");
                    return;
                default:
                    System.out.println("What are you try'na to say?");
            }
        }
    }

    public void doSqlTasks(Connection connection) throws SQLException {
        repository = libraryMain.initializeRepository(connection);
        Author author = new Author("Толкин", 1900);
        Book book = new Book("Властелин колец");
        User user = new User("Владимир");
        Review review = new Review("Концовка не очень, остальное не читал.");

        libraryMain.invokeConsoleMenu(author, book, user, review);
    }
}
