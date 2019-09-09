package hillel.spring.petclinic.pet.card;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Data
@Embeddable // встраивание в другую таблицу
public class Prescription {
    private String medicineName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer timesPerDay;
}