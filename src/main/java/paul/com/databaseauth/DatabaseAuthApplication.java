package paul.com.databaseauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import paul.com.databaseauth.repositories.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class DatabaseAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(DatabaseAuthApplication.class, args);
	}
}
