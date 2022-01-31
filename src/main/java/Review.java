public class Review {
    public int review_id;
    public String content;
    public int owner_id;

    public Review() {
    }

    public Review(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review_id=" + review_id +
                ", content='" + content + '\'' +
                ", owner_id=" + owner_id +
                '}';
    }
}
