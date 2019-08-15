package hillel.spring.petclinic.pet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    public Optional<Pet> findById(Integer id){
        return petRepository.findById(id);
    }

//    public List<Pet> findAll(Predicate<Pet> predicate) {
    public List<Pet> findAll(Optional<String> name, Optional<Integer> age) {

//        return petRepository.findAll().stream()
//                .filter(predicate)
//                .collect(Collectors.toList());
        if (name.isPresent() && age.isPresent()) {
            return petRepository.findByNameAndAge(name.get(), age.get());
        }
        if (name.isPresent()) {
            return petRepository.findByName(name.get());
        }
        if (age.isPresent()) {
            return petRepository.findByAge(age.get());
        }
        return petRepository.findAll();
    }

    public Pet createPet(Pet pet) {
//        return petRepository.createPet(pet);
        return petRepository.save(pet);
    }

    public void updatePet(Pet pet) {
//        petRepository.updatePet(pet);
        petRepository.save(pet);
    }

    public void deletePet(Integer id) {
//        petRepository.deletePet(id);
        petRepository.deleteById(id);
    }
}
