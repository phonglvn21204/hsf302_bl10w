package sum25.de190044.admissionconsultation.dto;

public class RegisterRequest {
    public String fullName;
    public String email;
    public String password;
    public String phone;

    public RegisterRequest(String email, String fullName, String number, String phone) {
    }

    public RegisterRequest() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
