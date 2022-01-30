public class User {
    public int user_id;
    public String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                '}';
    }
}
