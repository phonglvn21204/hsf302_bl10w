package sum25.de190044.admissionconsultation.dto;

public class LoginRequest {
    public String email;
    public String password;

    // Bắt buộc nếu dùng với Thymeleaf và không bật relaxed binding
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
