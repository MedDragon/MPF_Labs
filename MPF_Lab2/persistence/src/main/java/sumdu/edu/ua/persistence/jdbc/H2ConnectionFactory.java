package sumdu.edu.ua.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionFactory {
    // Використовуйте шлях до бази даних, який був у Лабі 1
    // Використовуйте повний шлях до папки вашого проекту
    private static final String URL = "jdbc:h2:file:/home/user/IdeaProjects/Frameworks/MPF_Lab2/web/data/book_db;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}