package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/events/")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping(value = "search")
    public ResponseEntity<EventDTO> getEventByEventName(@RequestParam("eventName") String eventName) {
        EventDTO eventDTO = eventService.getEventByEventName(eventName);
        return ResponseEntity.ok(eventDTO);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = eventService.addEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PutMapping(value = "search")
    public ResponseEntity<Event> updateEvent(@RequestParam("eventName") String eventName, @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(eventName, updatedEvent);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        String response = eventService.deleteEventById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllEvents")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> eventDTOS = eventService.getAllEvents();
        return ResponseEntity.ok(eventDTOS);
    }
}
