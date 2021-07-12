package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.entities.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CityRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CityRepository cityRepository;

    @Test
    @DisplayName("Should get a city by id")
    public void findCityByIdTest() {
        City city = createNewCity();
        entityManager.persist(city);
        Optional<City> cityFound = cityRepository.findById(city.getId());
        assertThat(cityFound.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get a city by name")
    public void findCityByNameTest() {
        String cityName = "Salvador";
        City city = City.builder().name("Salvador").build();
        entityManager.persist(city);
        City cityFound = cityRepository.findCityByName(cityName);
        assertThat(cityFound.getName()).isEqualTo(cityName);
    }

    @Test
    @DisplayName("Should get a city by name")
    public void findCityByNameStateTest() {
        String stateName = "Bahia";
        City city = createNewCity();
        entityManager.persist(city);
        City cityFound = cityRepository.findCityByState(stateName);
        assertThat(cityFound.getName()).isEqualTo(stateName);
    }

    @Test
    @DisplayName("Should save a city")
    public void saveCityTest() {
        City city = createNewCity();
        City savedCity = cityRepository.save(city);
        assertThat(savedCity.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should update a state")
    public void updateStateTest() {
        City city = createNewCity();
        City savedCity = entityManager.persist(city);
        City updateCity = City.builder().id(city.getId()).name("Porto de Galinhas").build();
        City updatedCity = cityRepository.save(updateCity);
        assertThat(updatedCity.getId()).isNotNull();
        assertThat(updatedCity.getId()).isEqualTo(savedCity.getId());
    }

    @Test
    @DisplayName("Should delete a state")
    public void deleteStateTest() {
        City city = createNewCity();
        entityManager.persist(city);
        City foundCity = entityManager.find(City.class, city.getId());
        cityRepository.delete(foundCity);
        City deletedCity = entityManager.find(City.class, city.getId());
        assertThat(deletedCity).isNull();
    }

    public static City createNewCity() {
        return City.builder().name("Salvador").state(createNewState()).build();
    }

    public static State createNewState() {
        return State.builder().id(1L).name("Bahia").uf("BA").build();
    }
}
