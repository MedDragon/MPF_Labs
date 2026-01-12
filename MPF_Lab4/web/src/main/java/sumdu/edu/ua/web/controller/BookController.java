package sumdu.edu.ua.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // Додаємо логер для виконання пункту 7 кроку 1
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final CatalogRepositoryPort bookRepo;

    public BookController(CatalogRepositoryPort bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookRepo.findAll();
            return ResponseEntity.ok(books); // Статус 200 OK + JSON
        } catch (Exception e) {
            log.error("Помилка доступу до БД при виконанні GET /api/books", e); // Логування
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Статус 500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        try {
            return bookRepo.findAll().stream()
                    .filter(b -> b.getId() == id)
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build()); // 404 якщо не знайдено
        } catch (Exception e) {
            log.error("Помилка при пошуку книги з ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}