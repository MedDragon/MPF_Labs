package sumdu.edu.ua.web.http;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sumdu.edu.ua.core.service.CommentService;
import sumdu.edu.ua.persistence.jdbc.JdbcCommentRepository;
import java.io.IOException;

@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {
    private final CommentService commentService = new CommentService(new JdbcCommentRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookIdParam = req.getParameter("bookId");

        if (bookIdParam != null) {
            long bookId = Long.parseLong(bookIdParam);
            // Завантажуємо коментарі для конкретної книги
            req.setAttribute("comments", commentService.getBookComments(bookId));
        }

        req.getRequestDispatcher("/WEB-INF/views/book-comments.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Встановлюємо кодування, щоб українські літери відображалися коректно
        req.setCharacterEncoding("UTF-8");

        String method = req.getParameter("_method");
        String bookIdParam = req.getParameter("bookId");

        if ("delete".equalsIgnoreCase(method)) {
            // Логіка видалення
            long commentId = Long.parseLong(req.getParameter("commentId"));
            commentService.deleteComment(Long.parseLong(bookIdParam), commentId);
        } else {
            // Логіка додавання
            String author = req.getParameter("author");
            String text = req.getParameter("text");
            if (bookIdParam != null && author != null && text != null) {
                commentService.addComment(Long.parseLong(bookIdParam), author, text);
            }
        }

        // Перенаправлення назад на ту саму сторінку
        resp.sendRedirect(req.getContextPath() + "/comments?bookId=" + bookIdParam);
    }
}