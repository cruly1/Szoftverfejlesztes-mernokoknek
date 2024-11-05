package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceUtils {

    private final EventRepository eventRepository;

    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Event"));
    }

    public Event findByEventName(String eventName) throws ResourceNotFoundException {
        return eventRepository.findByEventName(eventName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Event"));
    }
}
