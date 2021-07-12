package com.matheuscordeiro.sevensysapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.services.interfaces.CityService;
import com.matheuscordeiro.sevensysapi.services.interfaces.StateService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CityController.class)
@AutoConfigureMockMvc
public class CityControllerTest {
    static String STATE_API = "/api/cities";

    @Autowired
    MockMvc mvc;

    @MockBean
    CityService cityService;

    @Test
    @DisplayName("Should get city by id.")
    public void getCityByIdTest() throws Exception{
        Long id = 1l;

        City city = City.builder()
                .id(id)
                .name(createNewCity().getName())
                .state(createNewState())
                .build();

        BDDMockito.given( cityService.findCityByIdOrThrow(id) ).willReturn(Optional.of(city));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("id").value(id) )
                .andExpect( jsonPath("name").value(createNewCity().getName()) )
                .andExpect( jsonPath("state").value(createNewState()) );
    }

    @Test
    @DisplayName("Should get city by name.")
    public void getCityByNameTest() throws Exception{
        String name = "Salvador";

        City city = City.builder()
                .id(1L)
                .name(createNewCity().getName())
                .state(createNewState())
                .build();

        BDDMockito.given( cityService.findCityByNameOrThrow(name) ).willReturn(city);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API.concat("/name" + "?name=" + name))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("id").value(city.getId()) )
                .andExpect( jsonPath("name").value(createNewCity().getName()) )
                .andExpect( jsonPath("state").value(createNewState()) );
    }


    @Test
    @DisplayName("Should get all cities.")
    public void getCitiesTest() throws Exception{
        Long id = 1l;

        City city = City.builder()
                .id(id)
                .name(createNewState().getName())
                .state(createNewState())
                .build();

        List<City> cities = Arrays.asList(city);

        BDDMockito.given( cityService.findAllCitiesOrThrow())
                .willReturn(cities);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform( request )
                .andExpect( status().isOk() );
    }

    @Test
    @DisplayName("Should create a state")
    public void createCityTest() throws Exception {
        City savedCity = createNewCity();

        BDDMockito.given(cityService.saveCityOrThrow(Mockito.any(City.class))).willReturn(savedCity);
        String json = new ObjectMapper().writeValueAsString(savedCity);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(STATE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("id").value(10l) )
                .andExpect( jsonPath("name").value(savedCity.getName()) )
                .andExpect( jsonPath("state").value(savedCity.getState()) );
    }

    @Test
    @DisplayName("Should update a city")
    public void updateCityTest() throws Exception{
        Long id = 10L;
        String json = new ObjectMapper().writeValueAsString(createNewCity());
        City updatingCity = createNewCity();
        City updatedCity = City.builder().id(id).name("Salvador").state(createNewState()).build();
        BDDMockito.given(cityService.findCityByIdOrThrow(id)).willReturn(Optional.of(updatingCity));
        BDDMockito.given(cityService.updateCityOrThrow(updatingCity.getId(), updatingCity)).willReturn(updatedCity);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(STATE_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should delete a city")
    public void deleteCityTest() throws Exception {
        BDDMockito.given(cityService.findCityByIdOrThrow(Mockito.anyLong())).willReturn(Optional.of(City.builder().id(1L).build()));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(STATE_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    public static City createNewCity() {
        return City.builder().id(10L).name("Salvador").state(createNewState()).build();
    }

    public static State createNewState() {
        return State.builder().name("Bahia").uf("BA").build();
    }
}
