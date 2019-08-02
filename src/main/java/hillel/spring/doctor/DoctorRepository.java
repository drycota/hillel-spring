package hillel.spring.doctor;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DoctorRepository {
    private AtomicInteger id = new AtomicInteger();
    private final List<Doctor> doctors = new ArrayList<>();
    {
        doctors.add(new Doctor(id.incrementAndGet(), "Anton", "surgeon"));
        doctors.add(new Doctor(id.incrementAndGet(), "Boris", "therapist"));
        doctors.add(new Doctor(id.incrementAndGet(), "Dmitry", "surgeon"));
        doctors.add(new Doctor(id.incrementAndGet(), "Igor", "pediatrician"));
        doctors.add(new Doctor(id.incrementAndGet(), "Albert", "therapist"));
    }

    public List<Doctor> findAll(){
        return doctors;
    }

    public Optional<Doctor> findById(Integer id) {
        return doctors.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public void createDoctor(Doctor doctor) {
        addDoctor(doctor);
    }

    private boolean addDoctor(Doctor doctor){
        return doctors.add(new Doctor(id.incrementAndGet(), doctor.getName(), doctor.getSpecialization()));
    }

    public void updateDoctor(Doctor doctor) {
        findIndexById(doctor.getId()).ifPresentOrElse(idx -> doctors.set(idx, doctor), () -> {throw new NoSuchDoctorException();}) ;
    }

    private Optional<Integer> findIndexById(Integer id) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void deleteDoctor(Integer id) {
        findIndexById(id).ifPresentOrElse(idx -> doctors.remove(idx.intValue()), () -> {throw new DoctorNotFoundException();});
    }
}
