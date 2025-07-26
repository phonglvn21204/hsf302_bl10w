package sum25.de190044.admissionconsultation.service;

import sum25.de190044.admissionconsultation.pojo.ConsultationAppointment;
import sum25.de190044.admissionconsultation.pojo.User;

import java.util.List;

public interface ConsultationAppointmentService {
    ConsultationAppointment createAppointment(ConsultationAppointment appointment);
    void updateStatusAndAssignStaff(int id, String status, User staff);
    List<ConsultationAppointment> findAllAppointments();
}
