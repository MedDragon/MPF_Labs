package sumdu.edu.ua.core.port;

import sumdu.edu.ua.core.domain.Book;
import java.util.List;

public interface CatalogRepositoryPort {
    List<Book> findAll();
    Book findById(long id);
}