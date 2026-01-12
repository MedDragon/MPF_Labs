package sumdu.edu.ua.web.http;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;

import java.io.IOException;
import java.util.List;

public class BooksServlet extends HttpServlet {

    private final CatalogRepositoryPort bookRepo;

    public BooksServlet(CatalogRepositoryPort bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = bookRepo.findAll();
        req.setAttribute("books", books);
        req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
    }
}