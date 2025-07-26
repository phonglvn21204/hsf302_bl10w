package sum25.de190044.admissionconsultation.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.dto.ConsultationAppointmentRequest;
import sum25.de190044.admissionconsultation.pojo.ConsultationAppointment;
import sum25.de190044.admissionconsultation.pojo.Major;
import sum25.de190044.admissionconsultation.pojo.School;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.ConsultationAppointmentService;

@Controller
@RequestMapping("/appointments")
public class ConsultationAppointmentController {

    @Autowired
    private ConsultationAppointmentService appointmentService;

    @PostMapping("/submit")
    public String submitAppointment(@ModelAttribute ConsultationAppointmentRequest req, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            System.out.println("⚠️ Có lỗi khi bind dữ liệu:");
            result.getAllErrors().forEach(e -> System.out.println(e.toString()));
        }
        var currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("loginMessage", "Vui lòng đăng nhập để gửi yêu cầu tư vấn");
            return "redirect:/?loginRequired=true";
        }
        System.out.println("Current User: " + currentUser.getFullName());
        System.out.println("Appointment school: " + req.getSchoolId());
        System.out.println("Appointment Major: " + req.getMajorId());
        ConsultationAppointment appointment = new ConsultationAppointment();
        appointment.setCustomer(currentUser);
        appointment.setSchool(new School(req.getSchoolId()));
        appointment.setMajor(new Major(req.getMajorId()));

        appointmentService.createAppointment(appointment);
        return "redirect:/appointments/success";
    }



    @GetMapping("/success")
    public String successPage() {
        return "appointment_success";
    }
}
