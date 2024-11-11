package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.validation;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Event> {

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext context) {
        return event.getEventEndDate().isAfter(event.getEventStartDate()) || event.getEventStartDate().isEqual(event.getEventEndDate());
    }
}
