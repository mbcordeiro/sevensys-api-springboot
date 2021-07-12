package com.matheuscordeiro.sevensysapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.entities.Costumer;
import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.services.interfaces.CityService;
import com.matheuscordeiro.sevensysapi.services.interfaces.CostumerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CostumerController.class)
@AutoConfigureMockMvc
public class CostumerControllerTest {
    static String STATE_API = "/api/costumers";

    @Autowired
    MockMvc mvc;

    @MockBean
    CostumerService costumerService;

    @Test
    @DisplayName("Should get costumer by id.")
    public void getCostumerByIdTest() throws Exception{
        Long id = 1l;

        Costumer costumer = Costumer.builder()
                .id(id)
                .name(createNewCostumer().getName())
                .age(createNewCostumer().getAge())
                .gender(createNewCostumer().getGender())
                .birthDate(createNewCostumer().getBirthDate())
                .city(createNewCostumer().getCity())
                .build();

        BDDMockito.given( costumerService.findCostumerByIdOrThrow(id) ).willReturn(Optional.of(costumer));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("id").value(id) )
                .andExpect( jsonPath("name").value(createNewCostumer().getName()) )
                .andExpect( jsonPath("age").value(createNewCostumer().getAge()) )
                .andExpect( jsonPath("gender").value(createNewCostumer().getGender()) )
                .andExpect( jsonPath("birthDate").value(createNewCostumer().getBirthDate()) )
                .andExpect( jsonPath("city").value(createNewCostumer().getCity()) );
    }

    @Test
    @DisplayName("Should get costumer by name.")
    public void getCostumerByNameTest() throws Exception{
        String name = "Matheus";

        Costumer costumer = Costumer.builder()
                .id(1L)
                .name(createNewCostumer().getName())
                .age(createNewCostumer().getAge())
                .gender(createNewCostumer().getGender())
                .birthDate(createNewCostumer().getBirthDate())
                .city(createNewCostumer().getCity())
                .build();

        BDDMockito.given( costumerService.findCostumerByNameOrThrow(name) ).willReturn(costumer);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API.concat("/name" + "?name=" + name))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("id").value(costumer.getId()) )
                .andExpect( jsonPath("name").value(createNewCostumer().getName()) )
                .andExpect( jsonPath("age").value(createNewCostumer().getAge()) )
                .andExpect( jsonPath("gender").value(createNewCostumer().getGender()) )
                .andExpect( jsonPath("birthDate").value(createNewCostumer().getBirthDate()) )
                .andExpect( jsonPath("city").value(createNewCostumer().getCity()) );
    }


    @Test
    @DisplayName("Should get all costumers.")
    public void getCostumersTest() throws Exception{
        Long id = 1l;

        Costumer costumer = Costumer.builder()
                .id(1L)
                .name(createNewCostumer().getName())
                .age(createNewCostumer().getAge())
                .gender(createNewCostumer().getGender())
                .birthDate(createNewCostumer().getBirthDate())
                .city(createNewCostumer().getCity())
                .build();

        List<Costumer> costumers = Arrays.asList(costumer);

        BDDMockito.given( costumerService.findAllCostumersOrThrow())
                .willReturn(costumers);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform( request )
                .andExpect( status().isOk() );
    }

    @Test
    @DisplayName("Should create a Costumer")
    public void createCostumerTest() throws Exception {
        Costumer savedCostumer = createNewCostumer();

        BDDMockito.given(costumerService.saveCostumerOrThrow(Mockito.any(Costumer.class))).willReturn(savedCostumer);
        String json = new ObjectMapper().writeValueAsString(savedCostumer);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(STATE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect( status().isCreated() )
                .andExpect(status().isOk())
                .andExpect( jsonPath("id").value(savedCostumer.getId()) )
                .andExpect( jsonPath("name").value(createNewCostumer().getName()) )
                .andExpect( jsonPath("age").value(createNewCostumer().getAge()) )
                .andExpect( jsonPath("gender").value(createNewCostumer().getGender()) )
                .andExpect( jsonPath("birthDate").value(createNewCostumer().getBirthDate()) )
                .andExpect( jsonPath("city").value(createNewCostumer().getCity()) );
    }

    @Test
    @DisplayName("Should update a costumer")
    public void updateCostumerTest() throws Exception{
        Long id = 10L;
        String json = new ObjectMapper().writeValueAsString(createNewCity());
        Costumer updatingCostumer = createNewCostumer();
        Costumer updatedCostumer = updatingCostumer;
        updatedCostumer.setAge(24);
        BDDMockito.given(costumerService.findCostumerByIdOrThrow(id)).willReturn(Optional.of(updatingCostumer));
        BDDMockito.given(costumerService.updateCostumerOrThrow(updatingCostumer.getId(), updatingCostumer)).willReturn(updatedCostumer);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(STATE_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should delete a costumer")
    public void deleteCostumerTest() throws Exception {
        BDDMockito.given(costumerService.findCostumerByIdOrThrow(Mockito.anyLong())).willReturn(Optional.of(Costumer.builder().id(1L).build()));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(STATE_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    public static Costumer createNewCostumer() {
        return Costumer.builder().id(10L).name("Matheus").age(24).birthDate(LocalDate.now()).gender("masculino").city(createNewCity()).build();
    }
    public static City createNewCity() {
        return City.builder().id(10L).name("Salvador").state(createNewState()).build();
    }

    public static State createNewState() {
        return State.builder().name("Bahia").uf("BA").build();
    }
}
