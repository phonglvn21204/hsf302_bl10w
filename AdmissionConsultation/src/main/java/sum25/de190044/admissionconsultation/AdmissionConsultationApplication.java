package sum25.de190044.admissionconsultation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "sum25.de190044.admissionconsultation.pojo")
@EnableJpaRepositories(basePackages = "sum25.de190044.admissionconsultation.repository")
public class AdmissionConsultationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdmissionConsultationApplication.class, args);
    }

}
