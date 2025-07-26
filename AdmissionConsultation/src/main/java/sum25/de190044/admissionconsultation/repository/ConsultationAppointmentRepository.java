package sum25.de190044.admissionconsultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sum25.de190044.admissionconsultation.pojo.ConsultationAppointment;

public interface ConsultationAppointmentRepository extends JpaRepository<ConsultationAppointment, Integer> {
}
