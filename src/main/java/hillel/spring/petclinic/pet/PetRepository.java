package hillel.spring.petclinic.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("select pet from Pet as pet where  pet.name=:name and pet.age=:age")
    List<Pet> findByNameAndAge(@Param("name") String name, @Param("age") Integer age);

    List<Pet> findByName(String name);

    List<Pet> findByAge(Integer age);
//    //    private final List<Pet> pets = List.of(
////            new Pet(1, "Tom", "Cat", 2, new Owner("Vasya")),
////            new Pet(2, "Jerry", "Mouse", 1, new Owner("Petya"))
////    );
//    //private final List<Pet> pets = new ArrayList<>();
//    private final Map<Integer, Pet> idToPet = new ConcurrentHashMap<>();
//    private final AtomicInteger counter = new AtomicInteger();
//
//    public List<Pet> findAll() {
//        //return pets;
//        return new ArrayList<>(idToPet.values());
//    }
//
//    public Optional<Pet> findById(Integer id) {
////        return pets.stream()
////                .filter(it -> it.getId().equals(id))
////                .findFirst();
//        return Optional.ofNullable(idToPet.get(id));
//    }
//
//    public Pet createPet(Pet pet) {
////        pets.add(pet);
//        Pet created = pet.toBuilder().id(counter.incrementAndGet()).build();
//        idToPet.put(created.getId(), created);
//        return created;
//    }
//
//    public void updatePet(Pet pet) {
////        findIndexById(pet.getId()).ifPresentOrElse(idx -> pets.set(idx, pet), () -> {throw new NoSuchPetException();}) ;
//        idToPet.replace(pet.getId(), pet);
//    }
//
////    private Optional<Integer> findIndexById(Integer id) {
////        for (int i = 0; i < pets.size(); i++) {
////            if (pets.get(i).getId().equals(id)) {
////                return Optional.of(i);
////            }
////        }
////        return Optional.empty();
////    }
//
//    public void deletePet(Integer id) {
////        findIndexById(id).ifPresentOrElse(idx -> pets.remove(idx.intValue()), () -> {throw new NoSuchPetException();});
//        idToPet.remove(id);
//    }
//
//    public void deleteAll() {
////        pets.clear();
//        idToPet.clear();
//    }
}
