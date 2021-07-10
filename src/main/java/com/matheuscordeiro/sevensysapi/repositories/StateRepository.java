package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
