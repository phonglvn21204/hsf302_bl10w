package sum25.de190044.admissionconsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sum25.de190044.admissionconsultation.dto.LoginRequest;
import sum25.de190044.admissionconsultation.dto.RegisterRequest;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        if (request.email == null || request.fullName == null || request.password == null || request.phone == null) {
            throw new RuntimeException("Không được để trống các trường!");
        }

        if (userRepository.findByEmail(request.email.trim().toLowerCase()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User user = new User();
        user.setFullName(request.fullName.trim());
        user.setEmail(request.email.trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password));
        user.setPhone(request.phone.trim());
        user.setRole("customer");
        user.setStatus("active");
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }



    @Override
    public boolean login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email không tồn tại");
        }

        return passwordEncoder.matches(request.password, userOpt.get().getPasswordHash());
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new RuntimeException("Email không được để trống");
        }

        return userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public void delete(int id){
        userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) {
        User user = userRepository.findById(Math.toIntExact(id)).get();
        return user;
    }

    @Override
    public void save(User user) {
        if (user.getId() != 0) {
            // Update: fetch original user from DB
            User existing = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            // Copy allowed fields
            existing.setEmail(user.getEmail());
            existing.setFullName(existing.getFullName()); // Do NOT change name
            existing.setPhone(user.getPhone());
            existing.setRole(user.getRole());
            existing.setStatus(user.getStatus());
            // Only update password if user wants to change it
            if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
                existing.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            }
            // createdAt remains unchanged!
            userRepository.save(existing);
        } else {
            // Creating new user, must have all required fields
            if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
                throw new RuntimeException("Mật khẩu không được để trống");
            }
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }


}
