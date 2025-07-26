package sum25.de190044.admissionconsultation.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.ConsultationAppointmentService;
import sum25.de190044.admissionconsultation.service.UserService;

@Controller
@RequestMapping("/staff")
public class StaffAppointmentController {

    @Autowired
    private ConsultationAppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @PostMapping("/appointments/update")
    public String updateStatus(@RequestParam("appointmentId") int id,
                               @RequestParam("status") String status,
                               HttpSession session) {
        User staff = (User) session.getAttribute("currentUser");
        if (staff != null && staff.getRole().equals("staff")) {
            appointmentService.updateStatusAndAssignStaff(id, status, staff);
        }
        return "redirect:/staff/appointments";
    }
}
