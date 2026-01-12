package sumdu.edu.ua.core.port;

import sumdu.edu.ua.core.domain.Comment;
import java.util.List;

public interface CommentRepositoryPort {
    List<Comment> findAllByBookId(long bookId);
    void add(long bookId, String author, String text);
    void delete(long bookId, long commentId);
    Comment findById(long commentId);
}