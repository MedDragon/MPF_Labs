package sumdu.edu.ua.core.domain;

import java.time.LocalDateTime;

public class Comment {
    private long id;
    private long bookId;
    private String author;
    private String text;
    private LocalDateTime createdAt;

    public Comment() {}
    public Comment(long id, long bookId, String author, String text, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    // Геттери та сеттери
    public long getId() { return id; }
    public long getBookId() { return bookId; }
    public String getAuthor() { return author; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}