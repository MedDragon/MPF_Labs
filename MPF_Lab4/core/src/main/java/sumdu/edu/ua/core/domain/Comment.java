package sumdu.edu.ua.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Comment {
    private long id;
    private long bookId;
    private String author;
    private String text;

    // Додаємо формат, щоб Jackson не видавав помилку 500 при роботі з датою
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public Comment() {} // Обов'язковий для Jackson

    public Comment(long id, long bookId, String author, String text, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    // Геттери та СЕТТЕРИ (обов'язково додайте сеттери для всіх полів)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getBookId() { return bookId; }
    public void setBookId(long bookId) { this.bookId = bookId; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}