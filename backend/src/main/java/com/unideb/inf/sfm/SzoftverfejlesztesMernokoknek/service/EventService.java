package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.EventMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.EventServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventServiceUtils eventServiceUtils;

    public EventDTO getEventByEventName(String eventName) {
        Event event = eventServiceUtils.findByEventName(eventName);
        return eventMapper.toDTO(event);
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(String eventName, Event updatedEvent) {
        Event event = eventServiceUtils.findByEventName(eventName);

        event.setEventStartDate(updatedEvent.getEventStartDate());
        event.setEventEndDate(updatedEvent.getEventEndDate());
        event.setEventName(updatedEvent.getEventName());

        return eventRepository.save(event);
    }

    public String deleteEventById(Long id) {
        Event event = eventServiceUtils.findById(id);
        eventRepository.deleteById(event.getId());
        return "Event deleted successfully.";
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = new ArrayList<>();

        events.forEach(event -> eventDTOS.add(eventMapper.toDTO(event)));

        return eventDTOS;
    }
}
