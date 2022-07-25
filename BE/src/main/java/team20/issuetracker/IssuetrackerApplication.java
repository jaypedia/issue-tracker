package team20.issuetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IssuetrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssuetrackerApplication.class, args);
    }

}
