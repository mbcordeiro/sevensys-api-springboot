package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.exceptions.BusinessException;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.CityRepository;
import com.matheuscordeiro.sevensysapi.services.interfaces.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);
    private static final String CITY = "Cidade";

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<City> findCityByIdOrThrow(Long id) {
        try {
            return findCityById(id);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter o cidade.");
        }
    }

    @Override
    public List<City> findAllCitiesOrThrow() {
        try {
            return findAllCities();
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter as cidades.");
        }
    }

    @Override
    public City findCityByNameOrThrow(String name) {
        try {
            LOGGER.info("Buscando cidade por nome na base de dados.");
            return findCityByName(name);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter a cidade por nome.");
        }
    }

    @Override
    public City findCityByStateOrThrow(String state) {
        try {
            return findCityByState(state);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter a cidade por nome do estado.");
        }
    }

    @Override
    public City saveCityOrThrow(City city) {
        try {
            return saveCity(city);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao inserir a cidade.");
        }
    }

    @Override
    public City updateCityOrThrow(Long id, City city) {
        try {
            return updateCity(id, city);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao atualizar a cidade.");
        }
    }

    @Override
    public void deleteCityByIdOrThrow(Long id) {
        try {
            deleteCityById(id);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao deletar a cidade.");
        }
    }

    private Optional<City> findCityById(Long id) {
        LOGGER.info("Buscando cidade por id na base de dados.");
        return cityRepository.findById(id);
    }

    @Transactional(readOnly = true)
    private List<City> findAllCities() {
        LOGGER.info("Buscando cidaded todas as cidades na base de dados.");
        return cityRepository.findAll();
    }

    private City findCityByName(String name) {
        LOGGER.info("Buscando cidade por nome na base de dados.");
        return cityRepository.findCityByName(name);
    }

    private City findCityByState(String state) {
        LOGGER.info("Buscando cidade por nome do estado na base de dados.");
        return cityRepository.findCityByState(state);
    }

    @Transactional
    private City saveCity(City city) {
        LOGGER.info("Salvando cidade na base de dados.");
        return cityRepository.save(city);
    }

    @Transactional
    private City updateCity(Long id, City city) throws ObjectNotFoundException {
        verifyIfExists(id);
        LOGGER.info("Atualizando cidade na base de dados.");
        city.setId(id);
        return cityRepository.save(city);
    }

    private void deleteCityById(Long id) throws ObjectNotFoundException {
        verifyIfExists(id);
        LOGGER.info("Deletando cidade por id na base de dados.");
        cityRepository.deleteById(id);
    }

    private City verifyIfExists(Long id) throws ObjectNotFoundException {
        return cityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(CITY, id));
    }
}
