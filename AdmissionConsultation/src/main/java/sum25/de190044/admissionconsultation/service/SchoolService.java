package sum25.de190044.admissionconsultation.service;

import sum25.de190044.admissionconsultation.pojo.School;

import java.util.List;

public interface SchoolService {
    public List<School> findAll();
    School save(School school);
    School update(School school);
    School findById(Long id);
    void deleteById(Long id);

}
