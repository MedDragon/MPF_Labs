package sumdu.edu.ua.web.http;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sumdu.edu.ua.core.service.BookService;
import sumdu.edu.ua.persistence.jdbc.JdbcBookRepository;
import java.io.IOException;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {
    // Правильна ініціалізація сервісу через репозиторій
    private final BookService bookService = new BookService(new JdbcBookRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Тепер це завантажить дані з вашої бази H2
        req.setAttribute("books", bookService.getAllBooks());

        req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
    }
}