package sumdu.edu.ua.core.service;

import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import java.util.List;

public class BookService {
    private final CatalogRepositoryPort repository;

    public BookService(CatalogRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }
}