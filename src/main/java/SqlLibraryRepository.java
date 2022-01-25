import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SqlLibraryRepository implements ILibraryRepository {
    private final BookDAO bookDAO;
    public final AuthorDAO authorDAO;

    public SqlLibraryRepository(BookDAO bookDAO, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
    }


    @Override
    public Collection<Book> getAllBooks() {
        try {
            return bookDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch books");
        }
    }

    @Override
    public Collection<Author> getAllAuthors() {
        try {
            return authorDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch authors", e);
        }
    }

    @Override
    public void saveAuthor(Author author) {
        try {
            if (author.id == 0) {
                authorDAO.insert(author);
            } else {
                authorDAO.update(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save author", e);
        }
    }

    @Override
    public void saveBook(Book book, Author author) {
        try {
            if (author.id == 0) {
                authorDAO.insert(author);
            }

            book.authorID = author.id;
            bookDAO.insert(book);
        }
        catch (SQLException e) {
            throw new RuntimeException("Failed to save book", e);
        }
    }

    @Override
    public Collection<Book> findBooksByAuthorName(String name) {
  //      Collection<Book> books = new ArrayList<>();
  //      try {
//
  //          Collection <Author> authors = authorDAO.findByName(name);
  //          for (Author author : authors) {
  //              Collection<Book> booksPerAuthor = bookDAO.getBooksByAuthorID(author.id);
  //              books.addAll(booksPerAuthor);
  //          }
  //      }
  //      catch (SQLException e) {
  //          throw new RuntimeException("Failed to save book", e);
  //      }
        try {
            return bookDAO.findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Books are not found", e);
        }
    }
}
