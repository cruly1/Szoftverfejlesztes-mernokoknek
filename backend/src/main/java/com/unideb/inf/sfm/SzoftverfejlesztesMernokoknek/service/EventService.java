package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, Event updatedEvent) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException(eventId, "Event"));

        event.setEventDate(updatedEvent.getEventDate());
        event.setEventName(updatedEvent.getEventName());

        return eventRepository.save(event);
    }

    public String deleteEventById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException(eventId, "Event"));

        eventRepository.deleteById(eventId);
        return "Event deleted successfully.";
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = new ArrayList<>();

        events.stream().map((event -> new EventDTO(
                        event.getId(),
                        event.getEventName(),
                        event.getEventDate())))
                .forEach(eventDTOS::add);

        return eventDTOS;
    }

}
