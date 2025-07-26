
// 4. Service Implementation: ScholarshipServiceImpl.java
package sum25.de190044.admissionconsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.de190044.admissionconsultation.pojo.Scholarship;
import sum25.de190044.admissionconsultation.repository.ScholarshipRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScholarshipServiceImpl implements ScholarshipService {

    @Autowired
    private ScholarshipRepository scholarshipRepository;
    @Override
    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }
    @Override
    public List<Scholarship> getAllActiveScholarships() {
        return scholarshipRepository.findByStatusAndApplicationDeadlineAfter("active", LocalDate.now());
    }

    @Override
    public List<Scholarship> findMatchingScholarships(Long schoolId, Integer majorId, BigDecimal minAmount) {
        return scholarshipRepository.findMatchingScholarships(LocalDate.now(), schoolId, majorId, minAmount);
    }

    @Override
    public Scholarship save(Scholarship scholarship) {
        return scholarshipRepository.save(scholarship);
    }

    @Override
    public Scholarship findById(Long id) {
        return scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scholarship not found"));
    }

    @Override
    public void deleteById(Long id) {
        scholarshipRepository.deleteById(id);
    }

    @Override
    public List<Scholarship> findBySchool(Long schoolId) {
        return scholarshipRepository.findBySchoolIdAndStatus(schoolId, "active");
    }
}
