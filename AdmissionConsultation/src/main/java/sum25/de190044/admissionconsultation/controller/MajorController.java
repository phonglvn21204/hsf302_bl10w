package sum25.de190044.admissionconsultation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.pojo.Major;
import sum25.de190044.admissionconsultation.pojo.School;
import sum25.de190044.admissionconsultation.service.MajorService;
import sum25.de190044.admissionconsultation.service.SchoolService;

import java.util.List;

@Controller
@RequestMapping("/admin/majors")
public class MajorController {
    @Autowired
    private MajorService majorService;
    @Autowired
    private SchoolService schoolService;

    // LIST
    @GetMapping
    public String listMajors(Model model) {
        model.addAttribute("majors", majorService.getAllMajors());
        model.addAttribute("schools", schoolService.findAll());
        return "adminmajor";
    }

    // CREATE
    @PostMapping("/create")
    public String createMajor(@ModelAttribute Major major, @RequestParam Long schoolId) {
        School school = schoolService.findById(schoolId);
        major.setSchool(school);
        majorService.save(major);
        return "redirect:/admin/majors";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editMajor(@PathVariable int id, Model model) {
        model.addAttribute("editMajor", majorService.findById(id));
        model.addAttribute("majors", majorService.getAllMajors());
        model.addAttribute("schools", schoolService.findAll());
        return "adminmajor";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateMajor(@ModelAttribute Major major, @RequestParam Long schoolId) {
        School school = schoolService.findById(schoolId);
        major.setSchool(school);
        majorService.update(major);
        return "redirect:/admin/majors";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteMajor(@PathVariable int id) {
        majorService.deleteById(id);
        return "redirect:/admin/majors";
    }
}
