package hillel.spring.petclinic.pet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public Optional<Pet> findById(Integer id){
        return petRepository.findById(id);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public void createPet(Pet pet) {
        petRepository.createPet(pet);
    }

    public void updatePet(Pet pet) {
        petRepository.updatePet(pet);
    }

    public void deletePet(Integer id) {
        petRepository.deletePet(id);
    }
}
