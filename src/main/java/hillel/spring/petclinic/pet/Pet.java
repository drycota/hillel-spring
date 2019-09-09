package hillel.spring.petclinic.pet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

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
    private  String name;
    private  String breed;
    private  Integer age;
    private  String owner;
}
