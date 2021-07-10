package com.matheuscordeiro.sevensysapi.controllers;

import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.entities.Costumer;
import com.matheuscordeiro.sevensysapi.services.interfaces.CostumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/costumers")
public class CostumerController {
    private CostumerService costumerService;

    public  CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @GetMapping("{id}")
    public  ResponseEntity<Costumer> getCostumerById(@PathVariable Long id) {
        return ResponseEntity.ok(costumerService.findCostumerByIdOrThrow(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Costumer>> getCostumers() {
        return ResponseEntity.ok(costumerService.findAllCostumersOrThrow());
    }

    @GetMapping(value = "/email")
    public ResponseEntity<Costumer> getCostumerByName(@RequestParam String name) {
        return ResponseEntity.ok(costumerService.findCostumerByNameOrThrow(name));
    }

    @PostMapping
    public ResponseEntity<Costumer> saveCostumer(@RequestBody @Valid Costumer costumer) {
        Costumer costumerEntity = costumerService.saveCostumerOrThrow(costumer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(costumerEntity.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCostumer(@PathVariable Long id, @RequestBody @Valid Costumer costumer) {
        costumerService.updateCostumerOrThrow(id, costumer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCostumerById(@PathVariable Long id) {
        costumerService.deleteCostumerByIdOrThrow(id);
        return ResponseEntity.noContent().build();
    }
}
