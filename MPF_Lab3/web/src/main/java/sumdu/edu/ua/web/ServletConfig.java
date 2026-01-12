package sumdu.edu.ua.web;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import sumdu.edu.ua.core.port.CommentRepositoryPort;
import sumdu.edu.ua.web.http.BooksServlet;
import sumdu.edu.ua.web.http.CommentsServlet;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<CommentsServlet> commentsServlet(
            CatalogRepositoryPort bookRepo, CommentRepositoryPort commentRepo) {
        return new ServletRegistrationBean<>(new CommentsServlet(bookRepo, commentRepo), "/comments/*");
    }

    @Bean
    public ServletRegistrationBean<BooksServlet> booksServlet(CatalogRepositoryPort bookRepo) {
        return new ServletRegistrationBean<>(new BooksServlet(bookRepo), "/books", "/books/*");
    }
}