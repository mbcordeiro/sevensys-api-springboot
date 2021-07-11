package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {
    City findCityByName(String name);

    @Query(value = "SELECT city FROM City city JOIN FETCH city.id_state state WHERE state.name = :state")
    City findCityByState(@Param("state") String state);
}
