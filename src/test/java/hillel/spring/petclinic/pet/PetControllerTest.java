package hillel.spring.petclinic.pet;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PetRepository repository;

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreatePet() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/pets")
                .contentType("application/json")
                .content(fromResource("petclinic/pet/create-pet.json"))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://my-pet.com/pets/")))
                .andReturn().getResponse();

        Integer id = Integer.parseInt(response.getHeader("location").replace("http://my-pet.com/pets/", ""));

        assertThat(repository.findById(id)).isPresent();
    }

    @Test
    public void shouldUpdateTom() throws Exception {

//        Integer id = repository.createPet(new Pet(null, "Tom", "Cat", 2, new Owner("Vasya"))).getId();
        Integer id = repository.save(new Pet(null, "Tom", "Cat", 2, "Vasya")).getId();

        mockMvc.perform(put("/pets/{id}", id)
                .contentType("application/json")
                .content(fromResource("petclinic/pet/update-pet.json")))
                .andExpect(status().isOk());

        assertThat(repository.findById(id).get().getAge()).isEqualTo(3);
    }

    @Test
    public void shouldDeleteJerry() throws Exception {
//        repository.createPet(new Pet(null, "Tom", "Cat", 2, new Owner("Vasya")));
        repository.save(new Pet(null, "Tom", "Cat", 2, "Vasya"));
//        repository.createPet(new Pet(null, "Jerry", "Mouse", 1, new Owner("Petya")));
        Integer id = repository.save(new Pet(null, "Jerry", "Mouse", 1,"Petya")).getId();

        mockMvc.perform(delete("/pets/{id}", id))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    public void showIdFindAllPets() throws Exception{
//        repository.createPet(new Pet(null, "Tom", "Cat", 2, new Owner("Vasya")));
        repository.save(new Pet(null, "Tom", "Cat", 2,"Vasya"));
//        repository.createPet(new Pet(null, "Jerry", "Mouse", 1, new Owner("Petya")));
        repository.save(new Pet(null, "Jerry", "Mouse", 1,"Petya"));

        mockMvc.perform(MockMvcRequestBuilders.get("/pets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(fromResource("petclinic/pet/all-pets.json"), false))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].owner.name", is("Vasya")))
                .andExpect(jsonPath("$[0].owner", is("Vasya")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].id", notNullValue()));
    }

    @Test
    public void shouldReturnTom() throws Exception {
//        repository.createPet(new Pet(null, "Tom", "Cat", 2, new Owner("Vasya")));
        repository.save(new Pet(null, "Tom", "Cat", 2,"Vasya"));
//        repository.createPet(new Pet(null, "Jerry", "Mouse", 1, new Owner("Petya")));
        repository.save(new Pet(null, "Jerry", "Mouse", 1,"Petya"));

        mockMvc.perform(get("/pets").param("name", "Tom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Tom")));
    }

    @Test
    public void shouldReturnJerry() throws Exception {
//        repository.createPet(new Pet(null, "Tom", "Cat", 2, new Owner("Vasya")));
        repository.save(new Pet(null, "Tom", "Cat", 2,"Vasya"));
//        repository.createPet(new Pet(null, "Jerry", "Mouse", 1, new Owner("Petya")));
        repository.save(new Pet(null, "Jerry", "Mouse", 1,"Petya"));

        mockMvc.perform(get("/pets")
                .param("name", "Jerry")
                .param("age", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Jerry")))
                .andExpect(jsonPath("$[0].age", is(1)));
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