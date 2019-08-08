package hillel.spring.petclinic.pet;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PetRepository {
    //    private final List<Pet> pets = List.of(
//            new Pet(1, "Tom", "Cat", 2, new Owner("Vasya")),
//            new Pet(2, "Jerry", "Mouse", 1, new Owner("Petya"))
//    );
    private final List<Pet> pets = new ArrayList<>();

    {
        pets.add(new Pet(1, "Tom", "Cat", 2, new Owner("Vasya")));
        pets.add(new Pet(2, "Jerry", "Mouse", 1, new Owner("Petya")));
    }

    public List<Pet> findAll() {
        return pets;
    }

    public Optional<Pet> findById(Integer id) {
        return pets.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public void createPet(Pet pet) {
        pets.add(pet);
    }

    public void updatePet(Pet pet) {
        findIndexById(pet.getId()).ifPresentOrElse(idx -> pets.set(idx, pet), () -> {throw new NoSuchPetException();}) ;
    }

    private Optional<Integer> findIndexById(Integer id) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(id)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void deletePet(Integer id) {
        findIndexById(id).ifPresentOrElse(idx -> pets.remove(idx.intValue()), () -> {throw new NoSuchPetException();});
    }
}
