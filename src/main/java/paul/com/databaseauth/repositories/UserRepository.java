package paul.com.databaseauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import paul.com.databaseauth.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String username);
}
