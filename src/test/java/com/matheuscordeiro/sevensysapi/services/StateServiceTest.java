package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StateServiceTest {
    StateServiceImpl stateService;

    @MockBean
    StateRepository stateRepository;

    @BeforeEach
    public void setUp() {
        this.stateService = new StateServiceImpl(stateRepository);
    }

    @Test
    @DisplayName("Should get a state by id")
    public void getByIdTest(){
        Long id = 1l;
        State book = createValidState();
        book.setId(id);

        when(stateRepository.findById(id)).thenReturn(Optional.of(book));

        Optional<State> foundState = stateService.findByStateById(id);

        assertThat( foundState.isPresent() ).isTrue();
        assertThat( foundState.get().getId()).isEqualTo(id);
        assertThat( foundState.get().getName()).isEqualTo(book.getName());
        assertThat( foundState.get().getUf()).isEqualTo(book.getUf());
    }

    @Test
    @DisplayName("Should get all a states")
    public void findBookTest(){
        State state = createValidState();

        List<State> listState = Arrays.asList(state);
        when(stateRepository.findAll()).thenReturn(listState);

        List<State> resultState = stateService.findAllStates();

        assertThat(resultState.get(0).getName()).isEqualTo(listState.get(0).getName());
        assertThat(resultState.get(0).getUf()).isEqualTo(listState.get(0).getUf());
    }

    @Test
    @DisplayName("Should save a state")
    public void saveStateTest() {
        State state = createValidState();
        when(stateRepository.save(state)).thenReturn(
                state.builder().id(1l).name("Bahia").uf("BA").build()
        );

        State savedState = stateService.saveState(state);

        assertThat(savedState.getId()).isNotNull();
        assertThat(savedState.getName()).isEqualTo("Bahia");
        assertThat(savedState.getUf()).isEqualTo("BA");
    }

    @Test
    @DisplayName("Should update a state")
    public void updateBookTest() throws ObjectNotFoundException {
        long id = 1l;

        State updatingState = State.builder().id(id).build();

        State updatedState = createValidState();
        updatedState.setId(id);
        when(stateRepository.save(updatingState)).thenReturn(updatedState);

        State state = stateService.updateState(updatedState.getId(), updatingState);

        assertThat(state.getId()).isEqualTo(state.getId());
        assertThat(state.getName()).isEqualTo(updatedState.getName());
        assertThat(state.getUf()).isEqualTo(updatedState.getUf());
    }

    @Test
    @DisplayName("Should delete a state.")
    public void deleteBookTest(){
        State state = State.builder().id(1l).build();

        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> stateService.deleteStateById(state.getId()) );

        Mockito.verify(stateRepository, Mockito.times(1)).deleteById(state.getId());
    }


    private static State createValidState() {
        return State.builder().name("Bahia").uf("BA").build();
    }
}
