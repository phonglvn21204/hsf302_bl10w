package sum25.de190044.admissionconsultation.service;

import sum25.de190044.admissionconsultation.dto.LoginRequest;
import sum25.de190044.admissionconsultation.dto.RegisterRequest;
import sum25.de190044.admissionconsultation.pojo.User;

import java.util.List;

public interface UserService {
    User register(RegisterRequest request);
    boolean login(LoginRequest request);
    User findByEmail(String email);
    List<User> findAll();
    void delete(int id);
    User findById(int id);
    void save(User user);

}
