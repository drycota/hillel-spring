package hillel.spring.doctor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private  final DoctorRepository doctorRepository;

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
}
