package hillel.spring.doctor;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
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
                .andExpect(header().string("location", containsString("http://localhost/doctors/")))
                .andReturn().getResponse();

        Integer id = Integer.parseInt(response.getHeader("location").replace("http://localhost/doctors/", ""));

        assertThat(repository.findById(id)).isPresent();
    }

    @Test
    public void shouldUpdateDoctor() throws Exception{
        Integer id = repository.createDoctor(new Doctor(null, "Boris", "surgeon")).getId();
        mockMvc.perform(put("/doctors/{id}", id)
                .contentType("application/json")
                .content(fromResource("doctor/update-doctor.json")))
                .andExpect(status().isOk());

        assertThat(repository.findById(id).get().getName()).isEqualTo("Oleg");
    }

    @Test
    public void shouldReturnAll() throws Exception{
        repository.createDoctor(new Doctor(null, "Boris", "surgeon"));
        repository.createDoctor(new Doctor(null, "Anton", "therapist"));
        repository.createDoctor(new Doctor(null, "Albert", "surgeon"));

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
        repository.createDoctor(new Doctor(null, "Boris", "surgeon"));
        repository.createDoctor(new Doctor(null, "Anton", "therapist"));
        repository.createDoctor(new Doctor(null, "Albert", "surgeon"));

        mockMvc.perform(get("/doctors")
                .param("name", "A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Anton"))
                .andExpect(jsonPath("$[1].name").value("Albert"));
    }

    @Test
    public void shouldReturnSurgeon() throws Exception{
        repository.createDoctor(new Doctor(null, "Boris", "surgeon"));
        repository.createDoctor(new Doctor(null, "Anton", "therapist"));
        repository.createDoctor(new Doctor(null, "Albert", "surgeon"));

        mockMvc.perform(get("/doctors")
                .param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Boris"))
                .andExpect(jsonPath("$[1].name").value("Albert"));
    }

    @Test
    public void shouldReturnAlbert() throws Exception{
        repository.createDoctor(new Doctor(null, "Boris", "surgeon"));
        repository.createDoctor(new Doctor(null, "Anton", "therapist"));
        repository.createDoctor(new Doctor(null, "Albert", "surgeon"));

        mockMvc.perform(get("/doctors")
                .param("name", "A")
                .param("specialization", "surgeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Albert"))
                .andExpect(jsonPath("$[0].specialization").value("surgeon"));
    }

    @Test
    public void shouldDeleteDoctor() throws Exception{
        repository.createDoctor(new Doctor(null, "Boris", "surgeon"));
        repository.createDoctor(new Doctor(null, "Anton", "therapist"));
        repository.createDoctor(new Doctor(null, "Albert", "surgeon"));

        mockMvc.perform(delete("/doctors/{id}", 2))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(2)).isEmpty();
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