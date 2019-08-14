package hillel.spring.petclinic.pet;

import hillel.spring.petclinic.pet.dto.PetDtoConverter;
import hillel.spring.petclinic.pet.dto.PetInputDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;


@RestController
//@AllArgsConstructor
public class PetController {
    private final PetService petService;
    private final PetDtoConverter dtoConverter;// = Mappers.getMapper(PetDtoConverter.class);

    private final UriComponentsBuilder uriBuilder;

    public PetController(PetService petService,
                         PetDtoConverter dtoConverter,
                         //если ${pet-clinic.host-name} отсутствует, то по умолчанию будет localhost
                         @Value("${pet-clinic.host-name:localhost}") String hostName) {
        this.petService = petService;
        this.dtoConverter = dtoConverter;
        uriBuilder = UriComponentsBuilder.newInstance()
                                            .scheme("http")
                                            .host(hostName)
                                            .path("/pets/{id}");
    }

    @GetMapping("/pets/{id}")
    public Pet findById(@PathVariable Integer id) {
        val mayBePet = petService.findById(id);
        return mayBePet.orElseThrow(PetNotFoundException::new);
    }

    @GetMapping("/pets")
    public List<Pet> findAll(@RequestParam Optional<String> name, @RequestParam Optional<Integer> age) {
        Optional<Predicate<Pet>> mayBeNamePredicate = name.map(this::filterByName);
        Optional<Predicate<Pet>> mayByAgePredicate = age.map(this::filterByAge);
        Predicate<Pet> predicate = Stream.of(mayBeNamePredicate, mayByAgePredicate)
                .flatMap(Optional::stream)
                .reduce(Predicate::and)
                .orElse(pet -> true);
        return petService.findAll(predicate);
    }

    private Predicate<Pet> filterByName(String name){
        return pet -> pet.getName().equals(name);
    }

    private Predicate<Pet> filterByAge(Integer age){
        return pet -> pet.getAge().equals(age);
    }

    @PostMapping("/pets")
//    public ResponseEntity<?> createPet(@RequestBody Pet pet) {
    public ResponseEntity<?> createPet(@RequestBody PetInputDto dto) {
        val created = petService.createPet(dtoConverter.toModel(dto));
        return ResponseEntity.created(uriBuilder.build(created.getId())).build();
    }

//    @PutMapping("/pets/{id}")
//    public ResponseEntity<?> updatePet(@RequestBody Pet pet, @PathVariable Integer id){
//        if(!pet.getId().equals(id)){
//            throw new IdMissmatchException();
//        }
////        try {
////            petService.updatePet(pet);
////            return ResponseEntity.ok().build();
////        } catch (NoSuchPetException e){
////            return ResponseEntity.badRequest().build();
////        }
//        petService.updatePet(pet);
//        return ResponseEntity.ok().build();
//    }
    @PutMapping("/pets/{id}")
    public ResponseEntity<?> updatePet(@RequestBody PetInputDto dto,
                                       @PathVariable Integer id) {
        val pet = dtoConverter.toModel(dto, id);
        petService.updatePet(pet);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id){
        petService.deletePet(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void NoSuchPet(NoSuchPetException  e){

    }
}


