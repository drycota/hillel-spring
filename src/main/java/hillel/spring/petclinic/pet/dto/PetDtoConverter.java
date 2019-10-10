package hillel.spring.petclinic.pet.dto;

import hillel.spring.petclinic.pet.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetDtoConverter {

    @Mapping(target = "id", ignore = true)
    Pet toModel(PetInputDto dto);

    Pet toModel(PetInputDto dto, Integer id);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Pet pet, PetInputDto dto);
}