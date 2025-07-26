package sum25.de190044.admissionconsultation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.pojo.School;
import sum25.de190044.admissionconsultation.service.SchoolService;

@Controller
@RequestMapping("/admin/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

     // LIST all schools
    @GetMapping
    public String listSchools(Model model) {
        model.addAttribute("schools", schoolService.findAll());
        return "adminschools"; // your Thymeleaf file
    }

    // CREATE a new school
    @PostMapping("/create")
    public String createSchool(@ModelAttribute School school) {
        schoolService.save(school);
        return "redirect:/admin/schools";
    }

    // UPDATE a school
    @PostMapping("/update")
    public String updateSchool(@ModelAttribute School school) {
        schoolService.update(school);
        return "redirect:/admin/schools";
    }

    // DELETE a school
    @PostMapping("/delete/{id}")
    public String deleteSchool(@PathVariable Long id) {
        schoolService.deleteById(id);
        return "redirect:/admin/schools";
    }
}
