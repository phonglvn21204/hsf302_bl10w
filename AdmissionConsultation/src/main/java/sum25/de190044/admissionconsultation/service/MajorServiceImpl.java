package sum25.de190044.admissionconsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.de190044.admissionconsultation.pojo.Major;
import sum25.de190044.admissionconsultation.repository.MajorRepository;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorRepository majorRepository;

    @Override
    public List<Major> getAllMajors() {
        return majorRepository.findAll();
    }

    @Override
    public Major findById(int id) {
        return majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major not found"));
    }

    @Override
    public Major save(Major major) {
        return majorRepository.save(major);
    }

    @Override
    public Major update(Major major) {
        Major existing = findById(major.getId());
        existing.setName(major.getName());
        existing.setDescription(major.getDescription());
        existing.setSchool(major.getSchool());
        return majorRepository.save(existing);
    }

    @Override
    public void deleteById(int id) {
        majorRepository.deleteById(id);
    }

    @Override
    public List<Major> getMajorsBySchoolId(int schoolId) {
        return majorRepository.findBySchoolId(schoolId);
    }
}
