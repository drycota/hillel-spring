package hillel.spring.petclinic.pet.card;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDateTime date;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> complaints;
    @Embedded
    private Prescription prescription;
}
