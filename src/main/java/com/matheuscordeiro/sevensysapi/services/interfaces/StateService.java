package com.matheuscordeiro.sevensysapi.services.interfaces;

import com.matheuscordeiro.sevensysapi.entities.State;

import java.util.List;
import java.util.Optional;

public interface StateService {
    Optional<State> findByStateByIdOrThrow(Long id);

    List<State> findAllStatesOrThrow();

    State saveStateOrThrow(State state);

    State updateStateOrThrow(Long id, State state);

    void deleteStateByIdOrThrow(Long id);
}
