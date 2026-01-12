package sumdu.edu.ua.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import sumdu.edu.ua.db.CommentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CommentsServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CommentsServlet.class);
    private final CommentDao dao = new CommentDao();
    private final ObjectMapper om = new ObjectMapper();

    /**
     * GET /comments — повертає список коментарів у форматі JSON
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Встановлюємо тип відповіді JSON та кодування UTF-8
        resp.setContentType("application/json; charset=UTF-8");

        try {
            // 2. Отримуємо список з БД та записуємо у відповідь
            var list = dao.list();
            om.writeValue(resp.getWriter(), list);
        } catch (Exception e) {
            log.error("Помилка БД при отриманні списку", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB error"); // Код 500
        }
    }

    /**
     * POST /comments — додає новий коментар
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Кодування запиту
        req.setCharacterEncoding("UTF-8");

        // 2. Зчитуємо параметри з форми
        String author = req.getParameter("author");
        String text = req.getParameter("text");

        // 3. Валідація згідно з ТЗ (author <= 64, text <= 1000)
        if (author == null || author.isBlank() || author.length() > 64 ||
                text == null || text.isBlank() || text.length() > 1000) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid validation"); // Код 400
            return;
        }

        try {
            // 4. Збереження в БД
            dao.add(author.trim(), text.trim());

            // 5. Обов'язкове логування успіху
            log.info("New comment added: author='{}', text_length={}", author, text.length());

            // 6. Успіх — 204 No Content
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            log.error("Помилка БД при додаванні запису", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB error"); // Код 500
        }
    }
}