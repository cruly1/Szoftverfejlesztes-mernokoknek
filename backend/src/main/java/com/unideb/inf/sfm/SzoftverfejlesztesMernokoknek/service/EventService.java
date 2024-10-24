package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public EventDTO getEventById(Long id){
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id, "Event"));

        return new EventDTO(
                event.getId(),
                event.getEventName(),
                event.getEventDate()
        );
    }
}
