package com.matheuscordeiro.sevensysapi.services.interfaces;

import com.matheuscordeiro.sevensysapi.entities.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CityService {
    Optional<City> findCityByIdOrThrow(Long id);

    List<City> findAllCitiesOrThrow();

    City findCityByNameOrThrow(String name);

    City findCityByStateOrThrow(String state);

    City saveCityOrThrow(City city);

    City updateCityOrThrow(Long id, City city);

    void deleteCityByIdOrThrow(Long id);
}
