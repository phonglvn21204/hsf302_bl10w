package sum25.de190044.admissionconsultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.de190044.admissionconsultation.pojo.Major;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Integer> {

    List<Major> findBySchoolId(int schoolId);
}
