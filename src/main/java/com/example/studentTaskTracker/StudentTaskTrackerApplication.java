package com.example.studentTaskTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class StudentTaskTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentTaskTrackerApplication.class, args);
	}

}

@Configuration
class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                // отключаем CSRF, иначе Swagger выдаёт 403
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()               // разрешаем все запросы
                );

        return http.build();
    }
}

