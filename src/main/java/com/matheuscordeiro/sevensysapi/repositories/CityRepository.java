package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {
    City findCityByName(String name);

    @Query(value = "SELECT city.id, city.name, state.id as state_id " +
            "FROM city " +
            "INNER JOIN state " +
            "ON state.id = city.state_id " +
            "WHERE state.name = :stateName", nativeQuery = true)
    City findCityByState(@Param("stateName") String stateName);
}
