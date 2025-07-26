package sum25.de190044.admissionconsultation.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sum25.de190044.admissionconsultation.pojo.ConsultationAppointment;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.ConsultationAppointmentService;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private ConsultationAppointmentService appointmentService;

    @GetMapping("/appointments")
    public String viewAppointments(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"staff".equalsIgnoreCase(currentUser.getRole())) {
            return "redirect:/login";
        }

        List<ConsultationAppointment> appointments = appointmentService.findAllAppointments();
        model.addAttribute("appointments", appointments);
        model.addAttribute("staff", currentUser);
        model.addAttribute("content", "staffappointments :: content");
        model.addAttribute("title", "Quản lý lịch hẹn");
        return "staffappointments";
    }

    @GetMapping("/blogs")
    public String viewBlogs() {
        return "redirect:/blogs";
    }
}
