package hillel.spring.petclinic.pet.dto;

import lombok.Data;

@Data
public class PetInputDto {
    private String name;
    private String breed;
    private Integer age;
    private String owner;
}
