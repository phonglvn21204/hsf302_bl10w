package sum25.de190044.admissionconsultation.service;

import sum25.de190044.admissionconsultation.pojo.Major;

import java.util.List;

public interface MajorService {
    public List<Major> getAllMajors();
    Major findById(int id);
    Major save(Major major);
    Major update(Major major);
    void deleteById(int id);
    List<Major> getMajorsBySchoolId(int schoolId);
}
