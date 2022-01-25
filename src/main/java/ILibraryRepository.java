import java.sql.SQLException;
import java.util.Collection;

public interface ILibraryRepository {
    Collection<Book> getAllBooks() throws SQLException;
    Collection<Author> getAllAuthors() throws SQLException;

    void saveAuthor(Author author);
    void saveBook(Book book, Author author);

 //   /**
 //    Ищет все книги, имя автора которых включает заданный текст имени.
 //    @param name Текст имени для частичного поиска
 //    @return коллекция найденных книг.
 //    */
    Collection<Book> findBooksByAuthorName(String name);
}
