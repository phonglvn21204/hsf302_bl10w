package sum25.de190044.admissionconsultation.service;

import sum25.de190044.admissionconsultation.pojo.Scholarship;
import java.math.BigDecimal;
import java.util.List;

public interface ScholarshipService {
    List<Scholarship> getAllScholarships();
    List<Scholarship> getAllActiveScholarships();
    List<Scholarship> findMatchingScholarships(Long schoolId, Integer majorId, BigDecimal minAmount);
    Scholarship save(Scholarship scholarship);
    Scholarship findById(Long id);
    void deleteById(Long id);
    List<Scholarship> findBySchool(Long schoolId);
}