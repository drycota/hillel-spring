package hillel.spring.doctor;

import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
//@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorDtoConverter dtoConverter;

    private final UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                                                                        .scheme("http")
                                                                        .host("localhost")
                                                                        .path("/doctors/{id}");

    public DoctorController(DoctorService doctorService, DoctorDtoConverter dtoConverter) {
        this.doctorService = doctorService;
        this.dtoConverter = dtoConverter;
    }

    //localhost/doctors
    //localhost/doctors?specialization=surgeon
    //localhost/doctors?name=A
    //localhost/doctors?name=A&specialization=surgeon
    @GetMapping("/doctors")
    public List<Doctor> findAll(Optional<String> specialization, Optional<String> name) {
        Optional<Predicate<Doctor>> maybeSspecializationePredicate = specialization.map(this::filterBySpecialization);
        Optional<Predicate<Doctor>> maybeNamePredicate = name.map(this::filterByName);

        Predicate<Doctor> predicate = Stream.of(maybeSspecializationePredicate, maybeNamePredicate)
                .flatMap(Optional::stream)
                .reduce(Predicate::and)
                .orElse(pet -> true);

        return doctorService.findAll(predicate);
    }
    private Predicate<Doctor> filterBySpecialization(String specialization) {
        return doctor -> doctor.getSpecialization().equals(specialization);
    }
    private Predicate<Doctor> filterByName(String name) {
        return doctor -> doctor.getName().startsWith(name);
    }

    @GetMapping("/doctors/{id}")
    public Doctor findById(@PathVariable Integer id) {
        val mayBeDoctor = doctorService.findById(id);
        return mayBeDoctor.orElseThrow(DoctorNotFoundException::new);
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorInputDto dto) {
        val created = doctorService.createDoctor(dtoConverter.toModel(dto));
        return ResponseEntity.created(uriBuilder.build(created.getId())).build();
    }

    @PutMapping("/doctors/{id}")
//    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<?> updateDoctor(@RequestBody DoctorInputDto dto,
                                          @PathVariable Integer id){
        val doctor = dtoConverter.toModel(dto, id);
        doctorService.updateDoctor(doctor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id){
        doctorService.deleteDoctor(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void NoSuchDoctor(NoSuchDoctorException e){

    }
}

