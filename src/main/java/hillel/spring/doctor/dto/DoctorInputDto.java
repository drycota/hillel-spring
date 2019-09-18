package hillel.spring.doctor.dto;

import hillel.spring.doctor.Schedule;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class DoctorInputDto {
    private String name;
    private List<String> specialization;
    private Map<LocalDate, Schedule> scheduleToDate;
}
