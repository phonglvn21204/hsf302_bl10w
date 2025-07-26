package sum25.de190044.admissionconsultation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.pojo.Scholarship;
import sum25.de190044.admissionconsultation.service.ScholarshipService;
import sum25.de190044.admissionconsultation.service.SchoolService;
import sum25.de190044.admissionconsultation.service.MajorService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/scholarships")
public class ScholarshipController {

    @Autowired
    private ScholarshipService scholarshipService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MajorService majorService;

    // Trang tìm kiếm học bổng cho người dùng
    @GetMapping("/finder")
    public String scholarshipFinder(Model model) {
        model.addAttribute("schools", schoolService.findAll());
        model.addAttribute("majors", majorService.getAllMajors());
        model.addAttribute("scholarships", scholarshipService.getAllActiveScholarships());
        return "scholarship_finder";
    }

    // API tìm kiếm học bổng theo filter
    @GetMapping("/search")
    @ResponseBody
    public List<Scholarship> searchScholarships(
            @RequestParam(required = false) Long schoolId,
            @RequestParam(required = false) Integer majorId,
            @RequestParam(required = false) BigDecimal minAmount) {

        return scholarshipService.findMatchingScholarships(schoolId, majorId, minAmount);
    }

    // Chi tiết học bổng
    @GetMapping("/{id}")
    public String viewScholarship(@PathVariable Long id, Model model) {
        Scholarship scholarship = scholarshipService.findById(id);
        model.addAttribute("scholarship", scholarship);

        return "scholarship_detail";
    }
}
