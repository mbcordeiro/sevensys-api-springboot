package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.State;
import com.matheuscordeiro.sevensysapi.exceptions.BusinessException;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.StateRepository;
import com.matheuscordeiro.sevensysapi.services.interfaces.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateServiceImpl.class);
    private static final String STATE = "Estado";

    private final StateRepository stateRepository;

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

    private Optional<State> findByStateById(Long id) {
        LOGGER.info("Buscando estado por id na base de dados.");
        return stateRepository.findById(id);
    }

    @Transactional(readOnly = true)
    private List<State> findAllStates() {
        LOGGER.info("Buscando todos os estados na base de dados.");
        return stateRepository.findAll();
    }

    @Transactional
    private State saveState(State state) {
        LOGGER.info("Salvando estado na base de dados.");
        return stateRepository.save(state);
    }

    @Transactional
    private State updateState(Long id, State state) throws ObjectNotFoundException {
        verifyIfExists(id);
        LOGGER.info("Atualizando estado na base de dados.");
        state.setId(id);
        return stateRepository.save(state);

    }

    private void deleteStateById(Long id) throws ObjectNotFoundException {
        verifyIfExists(id);
        LOGGER.info("Deletando estado na base de dados.");
        stateRepository.deleteById(id);
    }

    private State verifyIfExists(Long id) throws ObjectNotFoundException {
        return stateRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(STATE, id));
    }
}
