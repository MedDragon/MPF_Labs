package sumdu.edu.ua.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.port.CommentRepositoryPort;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final CommentRepositoryPort commentRepo;

    public CommentController(CommentRepositoryPort commentRepo) {
        this.commentRepo = commentRepo;
    }

    // Отримати коментарі книги: GET /api/comments?bookId=1
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@RequestParam long bookId) {
        try {
            List<Comment> comments = commentRepo.findAllByBookId(bookId);
            return ResponseEntity.ok(comments); // Повертає 200 OK та JSON
        } catch (Exception e) {
            log.error("Помилка при отриманні коментарів для книги із ID: {}", bookId, e); // Логування
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Повертає 500
        }
    }

    // Додати коментар: POST /api/comments
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        // Валідація бізнес-логіки (Крок 1.5 методички)
        if (request.getAuthor() == null || request.getAuthor().isBlank()) {
            return ResponseEntity.badRequest().body("title & author required"); // Повертає 400
        }

        if (request.getText() == null || request.getText().length() < 3) {
            return ResponseEntity.badRequest().body("Текст занадто короткий");
        }

        try {
            commentRepo.add(request.getBookId(), request.getAuthor(), request.getText());
            return ResponseEntity.status(HttpStatus.CREATED).build(); // Повертає 201 Created
        } catch (Exception e) {
            log.error("Не вдалося додати коментар", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Помилка бази даних");
        }
    }
}

// DTO залишається таким самим, Jackson використовує його геттери/сеттери
class CommentRequest {
    private long bookId;
    private String author;
    private String text;

    public long getBookId() { return bookId; }
    public void setBookId(long bookId) { this.bookId = bookId; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}