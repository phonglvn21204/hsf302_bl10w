package sum25.de190044.admissionconsultation.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.dto.LoginRequest;
import sum25.de190044.admissionconsultation.dto.RegisterRequest;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.MajorService;
import sum25.de190044.admissionconsultation.service.SchoolService;
import sum25.de190044.admissionconsultation.service.UserService;

@Controller
public class LoginRegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private MajorService majorService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }


    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRequest request, HttpSession session, Model model) {
        try {
            User user = userService.findByEmail(request.email);
            if (!userService.login(request)) {
                model.addAttribute("error", "Sai mật khẩu");
                return "login";
            }
            session.setAttribute("currentUser", user);

            switch (user.getRole().toLowerCase()) {
                case "admin":
                    return "redirect:/admin/users";
                case "staff":
                    return "redirect:/staff/appointments";
                default:
                    return "redirect:/";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute RegisterRequest request, Model model) {
        try {
            userService.register(request);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        return "redirect:/admin/users";
    }
}
