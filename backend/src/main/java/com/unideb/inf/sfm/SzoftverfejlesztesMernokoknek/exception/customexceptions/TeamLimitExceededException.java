package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TeamLimitExceededException extends RuntimeException {

    public TeamLimitExceededException(String message) {
        super(message);
    }
}
