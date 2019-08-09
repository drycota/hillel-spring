package hillel.spring.petclinic.pet.dto;

import hillel.spring.petclinic.pet.Owner;
import lombok.Data;

@Data
public class PetInputDto {
    private String name;
    private String breed;
    private Integer age;
    private Owner owner;
}
