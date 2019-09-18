package hillel.spring.doctor;

import hillel.spring.doctor.dto.DoctorDtoConverter;
import hillel.spring.doctor.dto.DoctorInputDto;
import hillel.spring.doctor.dto.DoctorOutputDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
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

    @GetMapping("/doctors/{id}")
    public DoctorOutputDto findById(@PathVariable Integer id) {
        val mayBeDoctor = doctorService.findById(id);
        return dtoConverter.toDto(mayBeDoctor.orElseThrow(DoctorNotFoundException::new));
    }

    @GetMapping("/doctors/{doctorId}/schedule/{date}")
    public Schedule findScheduleByDate(@PathVariable Integer doctorId,
                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return doctorService.findOrCreateScheduleByDate(doctorId, date);
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

    @PostMapping("/doctors/{doctorId}/schedule/{date}/{hour}")
    public ResponseEntity<?> schedulePetToDoctor(@PathVariable Integer doctorId,
                                                 @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                 @PathVariable Integer hour,
                                                 @RequestBody PetId petId){
        doctorService.schedulePetToDoctor(doctorId, date, hour, petId.getPetId());
        return ResponseEntity.ok().build();
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
        return doctor.getSpecialization().stream()
                .allMatch(doc -> specializations.getSpecializations().stream().anyMatch(doc::equals));
    }

}

