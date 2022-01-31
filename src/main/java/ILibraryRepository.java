import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface ILibraryRepository {
    // author methods
    Collection<Author> getAllAuthors() throws SQLException;
    Collection<Author> findAuthorsByName(String name);
    Optional<Author> getAuthorByID(int author_id);
    void saveAuthor(Author author);
    void deleteAuthorByName(String name);
    void deleteAuthorByID(int id);

    // book methods
    Collection<Book> getAllBooks() throws SQLException;
    Collection<Book> getBooksByAuthorID(int author_id);
    Collection<Book> findBooksByAuthorName(String name);
    Collection<Book> findBooksByTitle(String title);
    void saveBook(Book book, Author author);
    void deleteBookByTitle(String title);
    void deleteBookByID(int id);

    // review methods
    Collection<Review> findReviewsByContent(String content);
    Collection<Review> getReviewsByUserID(int user_id);
    Collection<Review> getAllReviews() throws SQLException;
    void saveReview(User user, Review review);
    void deleteReviewByContent(String content);
    void deleteReviewByID(int id);

    // user methods
    Collection<User> getAllUsers() throws SQLException;
    Collection<User> findUsersByName(String name) throws SQLException;
    void saveUser(User user);
    void deleteUserByID(int id);
    void deleteUserByName(String name);
    User getUserByID(int review_id);

 //   /**
 //    Ищет все книги, имя автора которых включает заданный текст имени.
 //    @param name Текст имени для частичного поиска
 //    @return коллекция найденных книг.
 //    */
}
