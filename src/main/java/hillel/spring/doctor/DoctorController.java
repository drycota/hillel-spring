package hillel.spring.doctor;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

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
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {
            doctorService.createDoctor(doctor);
            return ResponseEntity.ok().build();
    }

    @PutMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void  updateDoctor(@RequestBody Doctor doctor, @PathVariable Integer id){
        doctorService.findIndexById(id).orElseThrow(DoctorNotFoundException::new);
        if(!id.equals(doctor.getId())){
            throw new IdNotEqualsException();
        }
        doctorService.updateDoctor(doctor);
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id){
        doctorService.deleteDoctor(id);
    }


}

