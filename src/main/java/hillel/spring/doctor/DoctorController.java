package hillel.spring.doctor;

import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import hillel.spring.doctor.dto.DoctorOutputDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
//@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorDtoConverter dtoConverter;
    private final DoctorSpecializations specializations;
    private final UriComponentsBuilder uriBuilder;

    public DoctorController(DoctorService doctorService,
                            DoctorDtoConverter dtoConverter,
                            DoctorSpecializations specializations,
                            @Value("${doctors.host-name:localhost}") String hostName) {
        this.doctorService = doctorService;
        this.dtoConverter = dtoConverter;
        this.specializations = specializations;
        uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostName)
                .path("/doctors/{id}");
    }

    //localhost/doctors
    //localhost/doctors?specialization=surgeon
    //localhost/doctors?name=A
    //localhost/doctors?name=A&specialization=surgeon
    @GetMapping("/doctors")
    public List<DoctorOutputDto> findAll(@RequestParam Optional<String> specialization,
                                @RequestParam Optional<String> name,
                                @RequestParam Optional<List<String>> specializations) {
        return doctorService.findAll(specialization, name, specializations).stream()
                .map(dtoConverter::toDto)
                .collect(Collectors.toList());
    }
//    private Predicate<Doctor> filterBySpecialization(String specialization) {
//        return doctor -> doctor.getSpecialization().equals(specialization);
//    }
//    private Predicate<Doctor> filterByName(String name) {
//        return doctor -> doctor.getName().startsWith(name);
//    }

    @GetMapping("/doctors/{id}")
    public DoctorOutputDto findById(@PathVariable Integer id) {
        val mayBeDoctor = doctorService.findById(id);
        return dtoConverter.toDto(mayBeDoctor.orElseThrow(DoctorNotFoundException::new));
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorInputDto dto) {
        val doctor = dtoConverter.toModel(dto);
        if(checkSpecialization(doctor)) {
            val created = doctorService.createDoctor(dtoConverter.toModel(dto));
            return ResponseEntity.created(uriBuilder.build(created.getId())).build();
        }
        else
            throw  new WrongSpecializationsException();
    }

    @PutMapping("/doctors/{id}")
//    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<?> updateDoctor(@RequestBody DoctorInputDto dto,
                                          @PathVariable Integer id){
        val doctor = dtoConverter.toModel(dto, id);
        if(checkSpecialization(doctor)) {
            doctorService.updateDoctor(doctor);
            return ResponseEntity.ok().build();
        }
        else
            throw  new WrongSpecializationsException();
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

    private boolean checkSpecialization(Doctor doctor) {
        return specializations.getSpecializations().stream()
                .anyMatch(spec -> spec.equals(doctor.getSpecialization()));
    }

}

