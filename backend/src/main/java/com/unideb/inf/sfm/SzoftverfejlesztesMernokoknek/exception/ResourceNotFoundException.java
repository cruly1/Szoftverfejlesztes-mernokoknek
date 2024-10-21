package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long playerId, String entity) {
        super(entity+ " not found with id number " + playerId);
    }
}
