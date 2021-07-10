package com.matheuscordeiro.sevensysapi.services;

import com.matheuscordeiro.sevensysapi.entities.City;
import com.matheuscordeiro.sevensysapi.exceptions.BusinessException;
import com.matheuscordeiro.sevensysapi.exceptions.ObjectNotFoundException;
import com.matheuscordeiro.sevensysapi.repositories.CityRepository;
import com.matheuscordeiro.sevensysapi.services.interfaces.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private static final String CITY = "Cidade";

    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<City> findCityByIdOrThrow(Long id) {
        try {
            return findCityById(id);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter o cidade");
        }
    }

    @Override
    public List<City> findAllCitiesOrThrow() {
        try {
            return findAllCities();
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao obter as cidades");
        }
    }

    @Override
    public City saveCityOrThrow(City city) {
        try {
            return saveCity(city);
        } catch (BusinessException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao inserir a cidade");
        }
    }

    @Override
    public City updateCityOrThrow(Long id, City city) {
        try {
            return updateCity(id, city);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao atualizar a cidade");
        }
    }

    @Override
    public void deleteCityByIdOrThrow(Long id) {
        try {
            deleteCityById(id);
        } catch (BusinessException | ObjectNotFoundException e) {
            throw new BusinessException("Ocorreu um erro inesperado ao deletar a cidade");
        }
    }

    private Optional<City> findCityById(Long id) {
        return cityRepository.findById(id);
    }

    private List<City> findAllCities() {
        return cityRepository.findAll();
    }

    private City saveCity(City city) {
        return cityRepository.save(city);
    }

    private City updateCity(Long id, City city) throws ObjectNotFoundException {
        verifyIfExists(id);
        city.setId(id);
        return cityRepository.save(city);
    }

    private void deleteCityById(Long id) throws ObjectNotFoundException {
        verifyIfExists(id);
        deleteCityById(id);
    }

    private City verifyIfExists(Long id) throws ObjectNotFoundException {
        return cityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(CITY, id));
    }
}
