package com.matheuscordeiro.sevensysapi.controllers;

import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.services.interfaces.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {
    private StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("{id}")
    public ResponseEntity<State> getStateById(@PathVariable Long id) {
        return ResponseEntity.ok(stateService.findByStateByIdOrThrow(id).get());
    }

    @GetMapping
    public ResponseEntity<List<State>> getStates() {
        return ResponseEntity.ok(stateService.findAllStatesOrThrow());
    }

    @PostMapping
    public ResponseEntity<State> saveState(@RequestBody @Valid State state) {
        return ResponseEntity.ok(stateService.saveStateOrThrow(state));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateState(@PathVariable Long id, @RequestBody @Valid State state) {
        stateService.updateStateOrThrow(id, state);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStateById(@PathVariable Long id) {
        stateService.deleteStateByIdOrThrow(id);
        return ResponseEntity.noContent().build();
    }
}
