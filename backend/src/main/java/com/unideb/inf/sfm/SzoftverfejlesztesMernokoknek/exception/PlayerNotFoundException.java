package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(Long playerId) {
        super("Player not found with id number " + playerId);
    }
}
