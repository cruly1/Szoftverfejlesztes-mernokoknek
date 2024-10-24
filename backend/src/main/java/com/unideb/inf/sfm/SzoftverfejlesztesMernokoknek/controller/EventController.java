package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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

}
