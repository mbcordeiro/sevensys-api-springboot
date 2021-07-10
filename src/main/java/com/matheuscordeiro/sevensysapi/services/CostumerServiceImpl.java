package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.Costumer;
import com.matheuscordeiro.sevensysapi.exceptions.BusinessException;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.CostumerRepository;
import com.matheuscordeiro.sevensysapi.services.interfaces.CostumerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostumerServiceImpl implements CostumerService {
    private static final String COSTUMER = "Cliente";

    private CostumerRepository costumerRepository;

    public CostumerServiceImpl(CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }

    @Override
    public Optional<Costumer> findByCostumerByIdOrThrow(Long id)  {
        try {
            return findByCostumerById(id);
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

    private Optional<Costumer> findByCostumerById(Long id) {
        return costumerRepository.findById(id);
    }

    private List<Costumer> findAllCostumers() {
        return costumerRepository.findAll();
    }

    private Costumer saveCostumer(Costumer costumer) {
        return costumerRepository.save(costumer);
    }

    private Costumer updateCostumer(Long id, Costumer costumer) throws ObjectNotFoundException {
        verifyIfExists(id);
        costumer.setId(id);
        return costumerRepository.save(costumer);
    }

    private void deleteCostumerById(Long id) throws ObjectNotFoundException {
        verifyIfExists(id);
        costumerRepository.deleteById(id);
    }
    private Costumer verifyIfExists(Long id) throws ObjectNotFoundException {
        return costumerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(COSTUMER, id));
    }
}
