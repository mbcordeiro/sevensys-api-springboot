package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.Costumer;
import com.matheuscordeiro.sevensysapi.exceptions.BusinessException;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.CostumerRepository;
import com.matheuscordeiro.sevensysapi.services.interfaces.CostumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CostumerServiceImpl implements CostumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CostumerServiceImpl.class);
    private static final String COSTUMER = "Cliente";

    private final CostumerRepository costumerRepository;

    public CostumerServiceImpl(CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }

    @Override
    public Optional<Costumer> findCostumerByIdOrThrow(Long id)  {
        try {
            return findCostumerById(id);
        } catch (Exception e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter os cliente");
        }
    }

    @Override
    public List<Costumer> findAllCostumersOrThrow() {
        try {
            return findAllCostumers();
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter os clientes");
        }
    }

    @Override
    public Costumer findCostumerByNameOrThrow(String name) {
        try {
            return findCostumerByName(name);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter o cliente por nome");
        }
    }

    @Override
    public Costumer saveCostumerOrThrow(Costumer costumer) {
        try {
            return saveCostumer(costumer);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao inserir o cliente");
        }
    }

    @Override
    public Costumer updateCostumerOrThrow(Long id, Costumer costumer) {
        try {
            return updateCostumer(id, costumer);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao atualizar o cliente");
        }
    }

    @Override
    public void deleteCostumerByIdOrThrow(Long id) {
        try {
            deleteCostumerById(id);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao deletar o cliente");
        }
    }

    private Optional<Costumer> findCostumerById(Long id) {
        LOGGER.info("Buscando cliente por id na base de dados.");
        return costumerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    private List<Costumer> findAllCostumers() {
        LOGGER.info("Buscando todos os clientes na base de dados.");
        return costumerRepository.findAll();
    }

    private Costumer findCostumerByName(String name) {
        LOGGER.info("Buscando cliente por nome na base de dados.");
        return costumerRepository.findCostumerByName(name);
    }

    @Transactional
    private Costumer saveCostumer(Costumer costumer) {
        LOGGER.info("Salvando cliente na base de dados.");
        return costumerRepository.save(costumer);
    }

    @Transactional
    private Costumer updateCostumer(Long id, Costumer costumer) throws ObjectNotFoundException {
        verifyIfExists(id);
        LOGGER.info("Atualizando cliente na base de dados.");
        costumer.setId(id);
        return costumerRepository.save(costumer);
    }

    private void deleteCostumerById(Long id) throws ObjectNotFoundException {
        verifyIfExists(id);
        LOGGER.info("Deletando cliente na base de dados.");
        costumerRepository.deleteById(id);
    }
    private Costumer verifyIfExists(Long id) throws ObjectNotFoundException {
        return costumerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(COSTUMER, id));
    }
}
