package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/events/")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long id) {
        EventDTO eventDTO = eventService.getEventById(id);

        return ResponseEntity.ok(eventDTO);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = eventService.addEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long eventId, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(eventId, updatedEvent);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        String response = eventService.deleteEventById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllPlayers")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> eventDTOS = eventService.getAllEvents();
        return ResponseEntity.ok(eventDTOS);
    }
}
