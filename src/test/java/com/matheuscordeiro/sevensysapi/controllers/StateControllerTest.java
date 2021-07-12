package com.matheuscordeiro.sevensysapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.services.interfaces.StateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = StateController.class)
@AutoConfigureMockMvc
public class StateControllerTest {
    static String STATE_API = "/api/states";

    @Autowired
    MockMvc mvc;

    @MockBean
    StateService stateService;

    @Test
    @DisplayName("Should get by id state.")
    public void getStateByIdTest() throws Exception{
        Long id = 1l;

        State state = State.builder()
                .id(id)
                .name(createNewState().getName())
                .uf(createNewState().getUf())
                .build();

        BDDMockito.given( stateService.findByStateByIdOrThrow(id) ).willReturn(Optional.of(state));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("id").value(id) )
                .andExpect( jsonPath("name").value(createNewState().getName()) )
                .andExpect( jsonPath("uf").value(createNewState().getUf()) );
    }

    @Test
    @DisplayName("Should get all states.")
    public void getStatesTest() throws Exception{
        Long id = 1l;

        State state = State.builder()
                .id(id)
                .name(createNewState().getName())
                .uf(createNewState().getUf())
                .build();

        List<State> states = Arrays.asList(state);

        BDDMockito.given( stateService.findAllStatesOrThrow())
                .willReturn(states);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(STATE_API)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform( request )
                .andExpect( status().isOk() );
    }

    @Test
    @DisplayName("Should create a state")
    public void createStateTest() throws Exception {
        State savedState = createNewState();

        BDDMockito.given(stateService.saveStateOrThrow(Mockito.any(State.class))).willReturn(savedState);
        String json = new ObjectMapper().writeValueAsString(savedState);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(STATE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("id").value(10l) )
                .andExpect( jsonPath("name").value(savedState.getName()) )
                .andExpect( jsonPath("uf").value(savedState.getUf()) );
    }

    @Test
    @DisplayName("Should update a state")
    public void updateStateTest() throws Exception{
        Long id = 10L;
        String json = new ObjectMapper().writeValueAsString(createNewState());
        State updatingState = createNewState();
        State updatedState = State.builder().id(id).name("Pernambuco").uf("PE").build();
        BDDMockito.given(stateService.findByStateByIdOrThrow(id)).willReturn(Optional.of(updatingState));
        BDDMockito.given(stateService.updateStateOrThrow(updatingState.getId(), updatingState)).willReturn(updatedState);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(STATE_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should delete a city")
    public void deleteStateTest() throws Exception {
        BDDMockito.given(stateService.findByStateByIdOrThrow(Mockito.anyLong())).willReturn(Optional.of(State.builder().id(1L).build()));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(STATE_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    private static State createNewState() {
        return State.builder().id(10L).name("Bahia").uf("BA").build();
    }
}
