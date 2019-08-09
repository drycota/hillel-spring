package hillel.spring.doctor;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DoctorRepository {
    private final Map<Integer, Doctor> idToDoctor = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger();

    public List<Doctor> findAll(){
        return new ArrayList<>(idToDoctor.values());
    }

    public Optional<Doctor> findById(Integer id) {
        return Optional.ofNullable(idToDoctor.get(id));
    }

    public Doctor createDoctor(Doctor doctor) {
        Doctor created = doctor.toBuilder().id(counter.incrementAndGet()).build();
        idToDoctor.put(created.getId(), created);
        return created;
    }

    public void updateDoctor(Doctor doctor) {
        idToDoctor.replace(doctor.getId(), doctor);
    }

    public void deleteDoctor(Integer id) {
        idToDoctor.remove(id);
    }

    public void deleteAll() {
        idToDoctor.clear();
    }
}
