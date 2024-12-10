package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestEventServiceUtils {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceUtils eventServiceUtils;

    @Test
    void findById_EventExists_ReturnsEvent() {
        // arrange
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setEventName("Test Event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // act
        Event result = eventServiceUtils.findById(eventId);

        // assert
        assertEquals(event, result);
        verify(eventRepository).findById(eventId);
    }

    @Test
    void findById_EventDoesNotExist_ThrowsResourceNotFoundException() {
        // arrange
        Long eventId = 1L;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                eventServiceUtils.findById(eventId)
        );

        verify(eventRepository).findById(eventId);
    }

    @Test
    void findByEventName_EventExists_ReturnsEvent() {
        // arrange
        String eventName = "Test Event";
        Event event = new Event();
        event.setId(1L);
        event.setEventName(eventName);

        when(eventRepository.findByEventName(eventName)).thenReturn(Optional.of(event));

        // act
        Event result = eventServiceUtils.findByEventName(eventName);

        // assert
        assertEquals(event, result);
        verify(eventRepository).findByEventName(eventName);
    }

    @Test
    void findByEventName_EventDoesNotExist_ThrowsResourceNotFoundException() {
        // arrange
        String eventName = "NonExistent Event";

        when(eventRepository.findByEventName(eventName)).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                eventServiceUtils.findByEventName(eventName)
        );

        verify(eventRepository).findByEventName(eventName);
    }
}
