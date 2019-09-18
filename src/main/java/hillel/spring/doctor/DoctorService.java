package hillel.spring.doctor;

import hillel.spring.petclinic.pet.NoSuchPetException;
import hillel.spring.petclinic.pet.Pet;
import hillel.spring.petclinic.pet.PetService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private  final DoctorRepository doctorRepository;
    private final PetService petService;

    public List<Doctor> findAll(Optional<String> specialization,
                                Optional<String> name,
                                Optional<List<String>> specializations) {
        if(specialization.isPresent() && name.isPresent()){
            return doctorRepository.findBySpecializationIgnoreCaseAndNameStartsWithIgnoreCase(specialization.get(), name.get());
        }
        if(specialization.isPresent()){
            return doctorRepository.findBySpecialization(specialization.get());
        }
        if (name.isPresent()){
            return doctorRepository.findByNameStartsWithIgnoreCase(name.get());
        }
        if (specializations.isPresent()) {
            return doctorRepository.findBySpecializationIn(specializations.get());
        }
        return doctorRepository.findAll();
    }

    public Optional<Doctor> findById(Integer id){
        return doctorRepository.findById(id);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public Optional<Doctor> findByID(Integer id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Integer id) {
        doctorRepository.deleteById(id);
    }

    public Schedule findOrCreateScheduleByDate(Integer doctorId, LocalDate date) {
        val mayBeDoctor = findById(doctorId);
        Doctor doctor =  mayBeDoctor.orElseThrow(NoSuchDoctorException::new);
        return findOrCreateScheduleByDate(doctor, date);
    }

    private Schedule findOrCreateScheduleByDate(Doctor doctor, LocalDate date){
        Schedule schedule = doctor.getScheduleToDate().get(date);
        if (schedule == null){
            schedule = new Schedule();
            doctor.getScheduleToDate().put(date, schedule);
        }
        return schedule;
    }

    public void schedulePetToDoctor(Integer doctorId, LocalDate date, Integer hour, Integer petId) {
        val mayBeDoctor = findById(doctorId);
        Doctor doctor =  mayBeDoctor.orElseThrow(DoctorNotFoundException::new);
        Schedule schedule = findOrCreateScheduleByDate(doctor, date);
        Optional<Pet> maybePet = petService.findById(petId);
        if (maybePet.isPresent()) {
            putToSchedule(schedule, hour, petId);
            doctorRepository.save(doctor);
        } else {
            throw new NoSuchPetException();
        }
    }

    private void putToSchedule(Schedule schedule, Integer hour, Integer petId){
        if ( hour < 8 || hour == 12 || hour > 17)
            throw new InvalidHourException();
        if (schedule.getHourToPetId().containsKey(hour)) {
            throw new InvalidHourException();
        } else {
            schedule.getHourToPetId().put(hour, petId);
        }
    }
}
