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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/scholarships")
public class AdminScholarshipController {

    @Autowired
    private ScholarshipService scholarshipService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MajorService majorService;

    @GetMapping
    public String listScholarships(Model model) {
        model.addAttribute("scholarships", scholarshipService.getAllScholarships()); // Lấy tất cả, không chỉ active
        model.addAttribute("schools", schoolService.findAll());
        model.addAttribute("majors", majorService.getAllMajors());
        return "admin_scholarships";
    }

    @PostMapping("/create")
    public String createScholarship(@ModelAttribute Scholarship scholarship,
                                    @RequestParam Long schoolId,
                                    @RequestParam(required = false) Integer majorId,
                                    Model model) {
        try {
            scholarship.setSchool(schoolService.findById(schoolId));
            if (majorId != null && majorId > 0) {
                scholarship.setMajor(majorService.findById(majorId));
            }

            // Set default values
            if (scholarship.getStatus() == null || scholarship.getStatus().isEmpty()) {
                scholarship.setStatus("active");
            }
            if (scholarship.getType() == null || scholarship.getType().isEmpty()) {
                scholarship.setType("partial");
            }
            scholarship.setCreatedAt(LocalDateTime.now());

            scholarshipService.save(scholarship);
            return "redirect:/admin/scholarships";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tạo học bổng: " + e.getMessage());
            model.addAttribute("scholarships", scholarshipService.getAllScholarships());
            model.addAttribute("schools", schoolService.findAll());
            model.addAttribute("majors", majorService.getAllMajors());
            return "admin_scholarships";
        }
    }

    @PostMapping("/update")
    public String updateScholarship(@ModelAttribute Scholarship scholarship,
                                    @RequestParam Long schoolId,
                                    @RequestParam(required = false) Integer majorId,
                                    Model model) {
        try {
            Scholarship existing = scholarshipService.findById(scholarship.getId());

            // Update fields
            existing.setTitle(scholarship.getTitle());
            existing.setDescription(scholarship.getDescription());
            existing.setAmount(scholarship.getAmount());
            existing.setMinGpa(scholarship.getMinGpa());
            existing.setApplicationDeadline(scholarship.getApplicationDeadline());
            existing.setRequirements(scholarship.getRequirements());
            existing.setType(scholarship.getType() != null ? scholarship.getType() : "partial");
            existing.setStatus(scholarship.getStatus() != null ? scholarship.getStatus() : "active");

            existing.setSchool(schoolService.findById(schoolId));
            if (majorId != null && majorId > 0) {
                existing.setMajor(majorService.findById(majorId));
            } else {
                existing.setMajor(null);
            }

            scholarshipService.save(existing);
            return "redirect:/admin/scholarships";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật học bổng: " + e.getMessage());
            model.addAttribute("scholarships", scholarshipService.getAllScholarships());
            model.addAttribute("schools", schoolService.findAll());
            model.addAttribute("majors", majorService.getAllMajors());
            return "admin_scholarships";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteScholarship(@PathVariable Long id) {
        try {
            scholarshipService.deleteById(id);
        } catch (Exception e) {
            // Log error but continue
            System.err.println("Error deleting scholarship: " + e.getMessage());
        }
        return "redirect:/admin/scholarships";
    }
}