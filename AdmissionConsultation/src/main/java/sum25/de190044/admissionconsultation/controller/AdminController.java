package sum25.de190044.admissionconsultation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sum25.de190044.admissionconsultation.pojo.Major;
import sum25.de190044.admissionconsultation.pojo.School;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.MajorService;
import sum25.de190044.admissionconsultation.service.SchoolService;
import sum25.de190044.admissionconsultation.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MajorService majorService;

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adminuser";
    }

    @GetMapping("/universities")
    public String viewUniversities(Model model) {
        model.addAttribute("schools", schoolService.findAll());
        return "adminschools";
    }

}


