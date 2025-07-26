package sum25.de190044.admissionconsultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.de190044.admissionconsultation.pojo.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
