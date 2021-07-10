package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.exceptions.BusinessException;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.StateRepository;
import com.matheuscordeiro.sevensysapi.services.interfaces.StateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private static final String STATE = "Estado";

    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public Optional<State> findByStateByIdOrThrow(Long id) {
        try {
            return findByStateById(id);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter o estado");
        }
    }

    @Override
    public List<State> findAllStatesOrThrow() {
        try {
            return findAllStates();
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter os estado");
        }
    }

    @Override
    public State saveStateOrThrow(State state) {
        try {
            return saveState(state);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao inserir o estado");
        }
    }

    @Override
    public State updateStateOrThrow(Long id, State state) {
        try {
            return updateState(id, state);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao atualizar o estado");
        }
    }

    @Override
    public void deleteStateByIdOrThrow(Long id) {
        try {
            deleteStateById(id);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao deletar o estado");
        }
    }

    public Optional<State> findByStateById(Long id) {
        return stateRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<State> findAllStates() {
        return stateRepository.findAll();
    }

    @Transactional
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public State updateState(Long id, State state) throws ObjectNotFoundException {
        verifyIfExists(id);
        state.setId(id);
        return stateRepository.save(state);

    }

    public void deleteStateById(Long id) throws ObjectNotFoundException {
        verifyIfExists(id);
        stateRepository.deleteById(id);
    }

    private State verifyIfExists(Long id) throws ObjectNotFoundException {
        return stateRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(STATE, id));
    }
}
