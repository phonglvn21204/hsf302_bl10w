package sum25.de190044.admissionconsultation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.dto.RegisterRequest;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, Model model) {
        try {
            RegisterRequest req = new RegisterRequest(
                    user.getEmail(),
                    user.getFullName(),
                    "123456", // Default password
                    user.getPhone()
            );
            userService.register(req);
            return "redirect:/admin/users";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("users", userService.findAll());
            return "adminuser";
        }
    }

    // EDIT - Show form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("editUser", userService.findById(id));
        model.addAttribute("users", userService.findAll());
        return "adminuser";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, Model model) {
        try {
            // Use .save() for simple update, or .register() for full validation if needed
            userService.save(user);
            return "redirect:/admin/users";
        } catch (Exception ex) {
            model.addAttribute("editUser", user);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("users", userService.findAll());
            return "adminuser";
        }
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
