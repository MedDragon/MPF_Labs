package sumdu.edu.ua.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // Активує розширені можливості Spring MVC
public class WebConfig implements WebMvcConfigurer {
    // В Spring Boot 3.x більшість налаштувань підхопиться автоматично
}