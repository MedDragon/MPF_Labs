package sumdu.edu.ua.core.service;

import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.port.CommentRepositoryPort;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class CommentService {
    private final CommentRepositoryPort repository;

    public CommentService(CommentRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Comment> getBookComments(long bookId) {
        return repository.findAllByBookId(bookId);
    }

    public void addComment(long bookId, String author, String text) {
        repository.add(bookId, author, text);
    }

    public void deleteComment(long bookId, long commentId) {
        Comment comment = repository.findById(commentId);
        if (comment != null) {
            // Бізнес-правило: видалення лише протягом 24 годин
            if (Duration.between(comment.getCreatedAt(), LocalDateTime.now()).toHours() < 24) {
                repository.delete(bookId, commentId);
            } else {
                throw new IllegalStateException("Коментар застарілий для видалення (минуло понад 24 години)");
            }
        }
    }
}