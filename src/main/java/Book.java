public class Book {
    public int id;
    public String title;
    public int authorID;

    public Book() {

    }

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorID=" + authorID +
                '}';
    }
}
