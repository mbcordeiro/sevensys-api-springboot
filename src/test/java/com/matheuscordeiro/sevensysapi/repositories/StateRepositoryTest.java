package com.matheuscordeiro.sevensysapi.repositories;

import com.matheuscordeiro.sevensysapi.entities.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class StateRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StateRepository stateRepository;

    @Test
    @DisplayName("Should get a state by id")
    public void findStateByIdTest() {
        State state = createNewState();
        entityManager.persist(state);
        Optional<State> stateFound = stateRepository.findById(state.getId());
        assertThat(stateFound.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should save a state")
    public void saveStateTest() {
        State state = createNewState();
        State savedState = stateRepository.save(state);
        assertThat(savedState.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should update a state")
    public void updateStateTest() {
        State state = createNewState();
        State savedState = entityManager.persist(state);
        State updateState = State.builder().id(state.getId()).name("Pernambuco").uf("PE").build();
        State updatedState = stateRepository.save(updateState);
        assertThat(updatedState.getId()).isNotNull();
        assertThat(updatedState.getId()).isEqualTo(savedState.getId());
    }

    @Test
    @DisplayName("Should delete a state")
    public void deleteStateTest() {
        State state = createNewState();
        entityManager.persist(state);
        State foundState = entityManager.find(State.class, state.getId());
        stateRepository.delete(foundState);
        State deletedState = entityManager.find(State.class, state.getId());
        assertThat(deletedState).isNull();
    }

    public static State createNewState() {
        return State.builder().name("Bahia").uf("BA").build();
    }
}
