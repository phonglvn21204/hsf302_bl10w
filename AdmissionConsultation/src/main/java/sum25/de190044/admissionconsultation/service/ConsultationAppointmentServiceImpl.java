package sum25.de190044.admissionconsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.de190044.admissionconsultation.pojo.ConsultationAppointment;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.repository.ConsultationAppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultationAppointmentServiceImpl implements ConsultationAppointmentService {

    @Autowired
    private ConsultationAppointmentRepository appointmentRepository;

    @Override
    public ConsultationAppointment createAppointment(ConsultationAppointment appointment) {
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setStatus("pending");
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<ConsultationAppointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void updateStatusAndAssignStaff(int id, String status, User staff) {
        ConsultationAppointment a = appointmentRepository.findById(id).orElse(null);
        if (a != null) {
            a.setStatus(status);
            a.setStaff(staff);
            appointmentRepository.save(a);
        }
    }
}
