package com.matheuscordeiro.sevensysapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String objectName, String name) {
        super(String.format("%s com o nome% s não encontrado no sistema.", objectName, name));
    }

    public ObjectNotFoundException(String objectName, Long id) {
        super(String.format("%s com id% s não encontrado no sistema.", objectName, id));
    }
}
