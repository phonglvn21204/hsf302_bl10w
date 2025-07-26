// 2. Repository: ScholarshipRepository.java
package sum25.de190044.admissionconsultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sum25.de190044.admissionconsultation.pojo.Scholarship;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {

    List<Scholarship> findByStatusAndApplicationDeadlineAfter(String status, LocalDate date);

    List<Scholarship> findBySchoolIdAndStatus(Long schoolId, String status);

    List<Scholarship> findByMajorIdAndStatus(Integer majorId, String status);

    @Query("SELECT s FROM Scholarship s WHERE s.status = 'active' " +
            "AND s.applicationDeadline > :currentDate " +
            "AND (:schoolId IS NULL OR s.school.id = :schoolId) " +
            "AND (:majorId IS NULL OR s.major.id = :majorId OR s.major IS NULL) " +
            "AND (:minAmount IS NULL OR s.amount >= :minAmount)")
    List<Scholarship> findMatchingScholarships(
            @Param("currentDate") LocalDate currentDate,
            @Param("schoolId") Long schoolId,
            @Param("majorId") Integer majorId,
            @Param("minAmount") java.math.BigDecimal minAmount
    );
}