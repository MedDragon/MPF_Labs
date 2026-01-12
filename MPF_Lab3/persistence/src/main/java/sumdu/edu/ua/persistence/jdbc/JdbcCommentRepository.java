package sumdu.edu.ua.persistence.jdbc;

import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.port.CommentRepositoryPort;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCommentRepository implements CommentRepositoryPort {

    @Override
    public List<Comment> findAllByBookId(long bookId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE book_id = ? ORDER BY created_at DESC";

        try (Connection conn = H2ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getLong("id"),
                        rs.getLong("book_id"),
                        rs.getString("author"),
                        rs.getString("text"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public void add(long bookId, String author, String text) {
        String sql = "INSERT INTO comments (book_id, author, text, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = H2ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, bookId);
            ps.setString(2, author);
            ps.setString(3, text);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long bookId, long commentId) {
        String sql = "DELETE FROM comments WHERE id = ? AND book_id = ?";
        try (Connection conn = H2ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, commentId);
            ps.setLong(2, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Comment findById(long commentId) {
        return null;
    }
}