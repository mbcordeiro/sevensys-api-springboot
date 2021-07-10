package com.matheuscordeiro.sevensysapi.controllers;

import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.entities.Costumer;
import com.matheuscordeiro.sevensysapi.services.interfaces.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.findCityByIdOrThrow(id).get());
    }

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.findAllCitiesOrThrow());
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody @Valid City city) {
        return ResponseEntity.ok(cityService.saveCityOrThrow(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCity(@PathVariable Long id, @RequestBody @Valid City city) {
        cityService.updateCityOrThrow(id, city);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable Long id) {
        cityService.deleteCityByIdOrThrow(id);
        return ResponseEntity.noContent().build();
    }
}
