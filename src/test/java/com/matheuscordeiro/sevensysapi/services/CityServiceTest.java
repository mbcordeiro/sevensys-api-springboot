package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CityServiceTest {
    CityServiceImpl cityService;

    @MockBean
    CityRepository cityRepository;

    @BeforeEach
    public void setUp() {
        this.cityService = new CityServiceImpl(cityRepository);
    }

    @Test
    @DisplayName("Should get a city by id")
    public void getByIdTest(){
        Long id = 1l;
        City city = createNewCity();
        city.setId(id);

        when(cityRepository.findById(id)).thenReturn(Optional.of(city));

        Optional<City> foundCity = cityService.findCityByIdOrThrow(id);

        assertThat( foundCity.isPresent() ).isTrue();
        assertThat( foundCity.get().getId()).isEqualTo(id);
        assertThat( foundCity.get().getName()).isEqualTo(city.getName());
        assertThat( foundCity.get().getState()).isEqualTo(city.getState());
    }

    @Test
    @DisplayName("Should get a city by name")
    public void getByNameTest(){
        String name = "Salvador";
        City city = createNewCity();

        when(cityRepository.findCityByName(name)).thenReturn(city);

        City foundCity = cityService.findCityByNameOrThrow(name);

        assertThat( foundCity.getId()).isEqualTo(city.getId());
        assertThat( foundCity.getName()).isEqualTo(name);
        assertThat( foundCity.getState()).isEqualTo(city.getState());
    }

    @Test
    @DisplayName("Should get all a Cities")
    public void findCitiesTest(){
        City city = createNewCity();

        List<City> cities = Arrays.asList(city);
        when(cityRepository.findAll()).thenReturn(cities);

        List<City> resultCity = cityService.findAllCitiesOrThrow();

        assertThat(resultCity.get(0).getName()).isEqualTo(cities.get(0).getName());
        assertThat(resultCity.get(0).getState()).isEqualTo(cities.get(0).getState());
    }

    @Test
    @DisplayName("Should save a city")
    public void saveCityTest() {
        City city = createNewCity();
        when(cityRepository.save(city)).thenReturn(
                city.builder().id(1l).name("Salvador").state(createNewState()).build()
        );

        City savedCity = cityService.saveCityOrThrow(city);

        assertThat(savedCity.getId()).isNotNull();
        assertThat(savedCity.getName()).isEqualTo("Salvador");
        assertThat(savedCity.getState()).isEqualTo("Bahia");
    }

    @Test
    @DisplayName("Should update a city")
    public void updateCityTest() throws ObjectNotFoundException {
        Long id = 1l;

        City updatingCity = City.builder().id(id).build();

        City updatedCity = createNewCity();
        updatedCity.setId(id);
        when(cityRepository.save(updatingCity)).thenReturn(updatedCity);

        City city = cityService.updateCityOrThrow(updatingCity.getId(), updatingCity);

        assertThat(city.getId()).isEqualTo(updatedCity.getId());
        assertThat(city.getName()).isEqualTo(updatedCity.getName());
        assertThat(city.getState()).isEqualTo(updatedCity.getState());
    }

    @Test
    @DisplayName("Should delete a state.")
    public void deleteBookTest(){
       City city = City.builder().id(1l).build();

       org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> cityService.deleteCityByIdOrThrow(city.getId()) );

       Mockito.verify(cityRepository, Mockito.times(1)).deleteById(city.getId());
    }

    public static City createNewCity() {
        return City.builder().name("Salvador").state(createNewState()).build();
    }

    public static State createNewState() {
        return State.builder().name("Bahia").uf("BA").build();
    }
}
