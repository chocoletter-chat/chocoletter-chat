package chocoletter.chat.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 모든 도메인 허용
                .allowedMethods("*") // 모든 메서드 허용
                .allowedHeaders("*")
                .allowCredentials(false) // allowCredentials(true)와 "*"은 같이 사용할 수 없음
                .maxAge(3600);
    }
}