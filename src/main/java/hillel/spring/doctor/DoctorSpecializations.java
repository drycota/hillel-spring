package hillel.spring.doctor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("doctors")
public class DoctorSpecializations {
    private List<String> specializations;
}
