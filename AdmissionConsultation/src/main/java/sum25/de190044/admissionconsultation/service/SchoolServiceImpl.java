package sum25.de190044.admissionconsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.de190044.admissionconsultation.pojo.School;
import sum25.de190044.admissionconsultation.repository.SchoolRepository;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService{
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public List<School> findAll() {
        List<School> schools = schoolRepository.findAll();
        return schools;
    }
    @Override
    public School save(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public School update(School school) {
        School existing = schoolRepository.findById(school.getId())
                .orElseThrow(() -> new RuntimeException("School not found"));
        existing.setName(school.getName());
        existing.setDescription(school.getDescription());
        existing.setLogoUrl(school.getLogoUrl());
        existing.setWebsiteUrl(school.getWebsiteUrl());
        return schoolRepository.save(existing);
    }

    @Override
    public School findById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }

    @Override
    public void deleteById(Long id) {
        schoolRepository.deleteById(id);
    }
}
