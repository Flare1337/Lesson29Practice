import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class SqlLibraryRepository implements ILibraryRepository {
    private final BookDAO bookDAO;
    public final AuthorDAO authorDAO;
    public final UserDAO userDAO;
    public final ReviewDAO reviewDAO;

    public SqlLibraryRepository(BookDAO bookDAO, AuthorDAO authorDAO, UserDAO userDAO, ReviewDAO reviewDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.userDAO = userDAO;
        this.reviewDAO = reviewDAO;
    }

    @Override
    public void deleteUserByID(int id) {
        try {
            userDAO.deleteByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user");
        }
    }

    @Override
    public void deleteBookByID(int id) {
        try {
            bookDAO.deleteByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete book");
        }
    }

    @Override
    public void deleteReviewByID(int id) {
        try {
            reviewDAO.deleteByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete review");
        }
    }

    @Override
    public void deleteAuthorByID(int id) {
        try {
            authorDAO.deleteByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete author");
        }
    }

    @Override
    public void deleteUserByName(String name) {
        try {
            userDAO.deleteByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user");
        }
    }

    @Override
    public void deleteBookByTitle(String title) {
        try {
            bookDAO.deleteByTitle(title);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete book");
        }
    }

    @Override
    public void deleteReviewByContent(String content) {
        try {
            reviewDAO.deleteByContent(content);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete review");
        }
    }

    @Override
    public void deleteAuthorByName(String name) {
        try {
            authorDAO.deleteByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete author");
        }
    }

    @Override
    public Collection<User> findUsersByName(String name) {
        try {
            return userDAO.findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch users");
        }
    }

    @Override
    public Collection<Review> findReviewsByContent(String content) {
        try {
            return reviewDAO.findByContent(content);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find reviews");
        }
    }

    @Override
    public Collection<Book> findBooksByTitle(String title) {
        try {
            return bookDAO.findByTitle(title);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find books");
        }
    }

    @Override
    public Collection<Author> findAuthorsByName(String name) {
        try {
            return authorDAO.findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find authors");
        }
    }

    @Override
    public User getUserByID(int id) {
        try {
            return userDAO.getUserByUserID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch users");
        }
    }

    @Override
    public Collection<Review> getReviewsByID(int user_id) {
        try {
            return reviewDAO.getReviewsByUserID(user_id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch reviews");
        }
    }

    @Override
    public Collection<Book> getBooksByAuthorID(int author_id) {
        try {
            return bookDAO.getBooksByAuthorID(author_id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch books");
        }
    }

    @Override
    public Optional<Author> getAuthorByID(int author_id) {
        try {
            return authorDAO.getById(author_id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get author");
        }
    }

    @Override
    public Collection<Review> getAllReviews() {
        try {
            return reviewDAO.getAll();
        }  catch (SQLException e) {
            throw new RuntimeException("Failed to fetch reviews");
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        try {
            return userDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch users");
        }
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
    public void saveUser(User user) {
        try {
            if (user.user_id == 0) {
                userDAO.insert(user);
            } else {
                userDAO.update(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public void saveReview(User user, Review review) {
        try {
            if (user.user_id == 0) {
                userDAO.insert(user);
            }

            review.owner_id = user.user_id;
            reviewDAO.insert(review);
        }
        catch (SQLException e) {
            throw new RuntimeException("Failed to save review", e);
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
            return bookDAO.findByTitle(name);
        } catch (SQLException e) {
            throw new RuntimeException("Books are not found", e);
        }
    }
}
