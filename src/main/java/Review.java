public class Review {
    public int review_id;
    public String content;
    public int owner_id;

    public Review() {
    }

    public Review(int review_id, String content, int owner_id) {
        this.review_id = review_id;
        this.content = content;
        this.owner_id = owner_id;
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
