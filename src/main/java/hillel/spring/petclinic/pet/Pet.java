package hillel.spring.petclinic.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private  Integer id;
    private  String name;
    private  String breed;
    private  Integer age;
    private  Owner owner;
}
