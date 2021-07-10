package com.matheuscordeiro.sevensysapi.services.interfaces;

import com.matheuscordeiro.sevensysapi.entities.Costumer;
import com.matheuscordeiro.sevensysapi.entities.State;

import java.util.List;
import java.util.Optional;

public interface CostumerService {
    Optional<Costumer> findCostumerByIdOrThrow(Long id);

    List<Costumer> findAllCostumersOrThrow();

    Costumer findCostumerByNameOrThrow(String name);

    Costumer saveCostumerOrThrow(Costumer costumer);

    Costumer updateCostumerOrThrow(Long id, Costumer costumer);

    void deleteCostumerByIdOrThrow(Long id);
}
