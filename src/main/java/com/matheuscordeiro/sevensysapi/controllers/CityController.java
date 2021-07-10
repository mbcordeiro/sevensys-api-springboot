package com.matheuscordeiro.sevensysapi.controllers;

import com.matheuscordeiro.sevensysapi.entities.City;
import java.net.URI;
import com.matheuscordeiro.sevensysapi.services.interfaces.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping(value = "/name")
    public ResponseEntity<City> getCityByName(@RequestParam String name) {
        return ResponseEntity.ok(cityService.findCityByNameOrThrow(name));
    }

    @GetMapping(value = "/state")
    public ResponseEntity<City> getCityByState(@RequestParam String state) {
        return ResponseEntity.ok(cityService.findCityByStateOrThrow(state));
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody @Valid City city) {
        City cityEntity = cityService.saveCityOrThrow(city);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cityEntity.getId()).toUri();
        return ResponseEntity.created(uri).build();
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
