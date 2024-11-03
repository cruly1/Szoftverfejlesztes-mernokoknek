package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PlayerAlreadyExistsException extends RuntimeException {

  public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
