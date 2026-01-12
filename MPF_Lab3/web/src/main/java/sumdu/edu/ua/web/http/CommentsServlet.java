package sumdu.edu.ua.web.http;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import sumdu.edu.ua.core.port.CommentRepositoryPort;

import java.io.IOException;
import java.util.List;

public class CommentsServlet extends HttpServlet {
    private final CatalogRepositoryPort catalogRepository;
    private final CommentRepositoryPort commentRepository;

    public CommentsServlet(CatalogRepositoryPort catalogRepository, CommentRepositoryPort commentRepository) {
        this.catalogRepository = catalogRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookIdParam = req.getParameter("bookId");
        if (bookIdParam != null) {
            long bookId = Long.parseLong(bookIdParam);
            List<Comment> comments = commentRepository.findAllByBookId(bookId);
            req.setAttribute("comments", comments);
        }
        // Використовуємо правильну назву файлу з вашої структури
        req.getRequestDispatcher("/WEB-INF/views/book-comments.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long bookId = Long.parseLong(req.getParameter("bookId"));
        String method = req.getParameter("_method");

        if ("delete".equalsIgnoreCase(method)) {
            long commentId = Long.parseLong(req.getParameter("commentId"));
            // ВИПРАВЛЕНО: тепер передаємо два аргументи, як вимагає інтерфейс
            commentRepository.delete(bookId, commentId);
        } else {
            String author = req.getParameter("author");
            String text = req.getParameter("text");
            commentRepository.add(bookId, author, text);
        }

        // Redirect назад на сторінку з коментарями
        resp.sendRedirect(req.getContextPath() + "/comments?bookId=" + bookId);
    }
}