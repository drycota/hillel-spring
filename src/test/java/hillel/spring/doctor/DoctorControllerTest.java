package hillel.spring.doctor;

import hillel.spring.petclinic.pet.Pet;
import hillel.spring.petclinic.pet.PetRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DoctorRepository repository;

    @Autowired
    PetRepository petRepository;

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateDoctor() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/doctors")
                .contentType("application/json")
                .content(fromResource("doctor/create-doctor.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://my-doctors.com/doctors/")))
                .andReturn().getResponse();

        Integer id = Integer.parseInt(response.getHeader("location").replace("http://my-doctors.com/doctors/", ""));

        assertThat(repository.findById(id)).isPresent();
    }

    @Test
    public void shouldUpdateDoctor() throws Exception{
        Integer id = repository.save(new Doctor(null, "Boris", List.of("surgeon"))).getId();
        mockMvc.perform(put("/doctors/{id}", id)
                .contentType("application/json")
                .content(fromResource("doctor/update-doctor.json")))
                .andExpect(status().isOk());

        assertThat(repository.findById(id).get().getName()).isEqualTo("Oleg");
    }

    @Test
    public void shouldReturnAll() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        repository.save(new Doctor(null, "Anton", List.of("therapist")));
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(fromResource("doctor/all-doctors.json"), false))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Boris")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[2].id", notNullValue()));
    }

    @Test
    public void shouldReturnNameStartA() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        repository.save(new Doctor(null, "Anton", List.of("therapist")));
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));

        mockMvc.perform(get("/doctors")
                .param("name", "A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Anton"))
                .andExpect(jsonPath("$[1].name").value("Albert"));
    }

    @Test
    public void shouldReturnSurgeon() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        repository.save(new Doctor(null, "Anton", List.of("therapist")));
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));

        mockMvc.perform(get("/doctors")
                .param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Boris"))
                .andExpect(jsonPath("$[1].name").value("Albert"));
    }

    @Test
    public void shouldReturnAlbert() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        repository.save(new Doctor(null, "Anton", List.of("therapist")));
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));

        mockMvc.perform(get("/doctors")
                .param("name", "A")
                .param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Albert"))
                .andExpect(jsonPath("$[0].specialization").value("surgeon"));
    }

    @Test
    public void shouldReturnAlbertIgnoreCase() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        repository.save(new Doctor(null, "Anton", List.of("therapist")));
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));

        mockMvc.perform(get("/doctors")
                .param("name", "aLBeRt")
                .param("specialization", "suRgeOn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Albert"))
                .andExpect(jsonPath("$[0].specialization").value("surgeon"));
    }

    @Test
    public void shouldDeleteDoctor() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        Integer id = repository.save(new Doctor(null, "Anton", List.of("therapist"))).getId();
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));

        mockMvc.perform(delete("/doctors/{id}", id))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(id).isEmpty());
    }

    @Test
    public void doctorWithAWrongSpecialization() throws Exception {
        mockMvc.perform(post("/doctors")
                .contentType("application/json")
                .content(fromResource("doctor/wrong-doctor.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnSurgeonAndTherapist() throws Exception{
        repository.save(new Doctor(null, "Boris", List.of("surgeon")));
        repository.save(new Doctor(null, "Anton", List.of("therapist")));
        repository.save(new Doctor(null, "Albert", List.of("surgeon")));
        repository.save(new Doctor(null, "Igor", List.of("oculist")));

        mockMvc.perform(get("/doctors")
                .param("specializations", "surgeon", "therapist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("Boris"))
                .andExpect(jsonPath("$[1].name").value("Anton"))
                .andExpect(jsonPath("$[2].name").value("Albert"));
    }

    @Test
    public void shouldCreateSchedule() throws Exception{
        Integer doctorId = repository.save(new Doctor(null, "Boris", List.of("surgeon", "veterinarian"))).getId();
        Integer petId = petRepository.save(new Pet(null, "Tom", "Cat", 2, "Oleg")).getId();

        mockMvc.perform(post("/doctors/{id}/schedule/2019-10-01/10", doctorId)
                .contentType("application/json")
                .content("{ \"petId\" : \"" + petId + "\"}"))
                .andExpect(status().isOk());

        Doctor doctor = repository.findById(doctorId).get();
        Schedule schedule = doctor.getScheduleToDate().get(LocalDate.of(2019,10,01));
        assertThat(schedule.getHourToPetId().size()).isEqualTo(1);
        assertThat(schedule.getHourToPetId().containsKey(10)).isTrue();
        assertThat(schedule.getHourToPetId().containsValue(petId)).isTrue();
    }

    @Test
    public void shouldFindSchedule() throws Exception{
        Integer doctorId = repository.save(new Doctor(null, "Boris", List.of("surgeon", "veterinarian"))).getId();
        Integer petId = petRepository.save(new Pet(null, "Tom", "Cat", 2, "Oleg")).getId();

        mockMvc.perform(post("/doctors/{id}/schedule/2019-10-01/10", doctorId)
                .contentType("application/json")
                .content("{ \"petId\" : \"" + petId + "\"}"));

        mockMvc.perform(get("/doctors/{id}/schedule/2019-10-01", doctorId))
                .andExpect(jsonPath("$.hourToPetId.10", is(petId)));
    }

    @Test
    public void shouldNotSchedulePetWithWrongDoctor() throws Exception{
        Integer doctorId = repository.save(new Doctor(null, "Boris", List.of("surgeon", "veterinarian"))).getId();
        Integer petId = petRepository.save(new Pet(null, "Tom", "Cat", 2, "Oleg")).getId();

        mockMvc.perform(post("/doctors/{id}/schedule/2019-10-01/10", doctorId +100)
                .contentType("application/json")
                .content("{ \"petId\" : \"" + petId + "\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotSchedulePetWithWrongPet() throws Exception{
        Integer doctorId = repository.save(new Doctor(null, "Boris", List.of("surgeon", "veterinarian"))).getId();
        Integer petId = petRepository.save(new Pet(null, "Tom", "Cat", 2, "Oleg")).getId();

        mockMvc.perform(post("/doctors/{id}/schedule/2019-10-01/10", doctorId )
                .contentType("application/json")
                .content("{ \"petId\" : \"" + petId +100 + "\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotSchedulePetWithWrongHour() throws Exception{
        Integer doctorId = repository.save(new Doctor(null, "Boris", List.of("surgeon", "veterinarian"))).getId();
        Integer petId = petRepository.save(new Pet(null, "Tom", "Cat", 2, "Oleg")).getId();

        mockMvc.perform(post("/doctors/{id}/schedule/2019-10-01/12", doctorId )
                .contentType("application/json")
                .content("{ \"petId\" : \"" + petId  + "\"}"))
                .andExpect(status().isBadRequest());
    }

    public String fromResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}