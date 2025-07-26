package sum25.de190044.admissionconsultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.de190044.admissionconsultation.pojo.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
