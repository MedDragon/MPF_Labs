package sumdu.edu.ua.persistence.jdbc;

import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepository implements CatalogRepositoryPort {

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = H2ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("pub_year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Це буде сипати помилку, поки немає таблиці
        }
        return books;
    }

    @Override
    public Book findById(long id) {
        // Логіка пошуку однієї книги аналогічноfindAll
        return null;
    }
}