package hillel.spring.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    //@Query("select doctor from Doctor as doctor where doctor.specialization=:specialization and doctor.name like %:name%")
    List<Doctor> findBySpecializationIgnoreCaseAndNameStartsWithIgnoreCase(@Param("specialization")String specialization, @Param("name")String name);

    List<Doctor> findBySpecialization(String specialization);

    List<Doctor> findBySpecializationIn(@Param("specializations") List<String> specializations);

    List<Doctor> findByNameStartsWithIgnoreCase(@Param("name") String name);

}
