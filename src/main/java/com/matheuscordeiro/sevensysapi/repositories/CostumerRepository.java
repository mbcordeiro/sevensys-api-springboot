package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    Costumer findCostumerByName(String name);
}
