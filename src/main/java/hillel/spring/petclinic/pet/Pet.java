package hillel.spring.petclinic.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Pet {
    @Id
    //@GeneratedValue // h2
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // postgres
    private  Integer id;
    @Version
    private Integer version;
    private  String name;
    private  String breed;
    private  Integer age;
    private  String owner;
}
