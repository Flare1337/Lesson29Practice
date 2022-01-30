public class User {
    public int user_id;
    public String name;
    public int review_id;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", name='" + name + '\'' +
                ", review_id=" + review_id +
                '}';
    }
}
