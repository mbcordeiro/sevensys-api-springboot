package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
