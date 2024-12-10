package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.servicetest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.EventDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.EventMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.EventService;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.EventServiceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestEventService {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventServiceUtils eventServiceUtils;

    @InjectMocks
    private EventService eventService;

    private Event mockEvent;
    private EventDTO mockEventDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setEventName("TestEvent");
        mockEvent.setEventStartDate(LocalDate.of(2024, 1, 1));
        mockEvent.setEventEndDate(LocalDate.of(2024, 1, 10));

        mockEventDTO = new EventDTO();
        mockEventDTO.setEventName("TestEvent");
        mockEventDTO.setEventStartDate(LocalDate.of(2024, 1, 1));
        mockEventDTO.setEventEndDate(LocalDate.of(2024, 1, 10));
    }

    @Test
    public void getEventByEventName_ExistingEvent_ReturnsEventDTO() {
        String eventName = "TestEvent";
        when(eventServiceUtils.findByEventName(eventName)).thenReturn(mockEvent);
        when(eventMapper.toDTO(mockEvent)).thenReturn(mockEventDTO);

        EventDTO result = eventService.getEventByEventName(eventName);

        assertNotNull(result);
        assertEquals(mockEventDTO, result);
        verify(eventServiceUtils, times(1)).findByEventName(eventName);
        verify(eventMapper, times(1)).toDTO(mockEvent);
    }

    @Test
    public void getEventByEventName_NonExistingEvent_ReturnsNull() {
        // Arrange
        String eventName = "NonExistingEvent";
        when(eventServiceUtils.findByEventName(eventName)).thenReturn(null);
        when(eventMapper.toDTO(null)).thenReturn(null); // Hozzáadott mock viselkedés

        // Act
        EventDTO result = eventService.getEventByEventName(eventName);

        // Assert
        assertNull(result);
        verify(eventServiceUtils, times(1)).findByEventName(eventName);
        verify(eventMapper, times(1)).toDTO(null); // Módosítva: elfogadja a null hívást
    }



    @Test
    public void addEvent_ValidEvent_ReturnsSavedEvent() {
        when(eventRepository.save(mockEvent)).thenReturn(mockEvent);

        Event result = eventService.addEvent(mockEvent);

        assertNotNull(result);
        assertEquals(mockEvent, result);
        verify(eventRepository, times(1)).save(mockEvent);
    }

    @Test
    public void updateEvent_ValidUpdate_ReturnsUpdatedEvent() {
        String eventName = "TestEvent";
        Event updatedEvent = new Event();
        updatedEvent.setEventName("UpdatedEvent");
        updatedEvent.setEventStartDate(LocalDate.of(2024, 2, 1));
        updatedEvent.setEventEndDate(LocalDate.of(2024, 2, 10));

        when(eventServiceUtils.findByEventName(eventName)).thenReturn(mockEvent);
        when(eventRepository.save(mockEvent)).thenReturn(mockEvent);

        Event result = eventService.updateEvent(eventName, updatedEvent);

        assertNotNull(result);
        assertEquals("UpdatedEvent", result.getEventName());
        assertEquals(LocalDate.of(2024, 2, 1), result.getEventStartDate());
        assertEquals(LocalDate.of(2024, 2, 10), result.getEventEndDate());
        verify(eventServiceUtils, times(1)).findByEventName(eventName);
        verify(eventRepository, times(1)).save(mockEvent);
    }

    @Test
    public void getAllEvents_ReturnsEventDTOList() {
        List<Event> events = List.of(mockEvent);
        List<EventDTO> eventDTOs = List.of(mockEventDTO);

        when(eventRepository.findAll()).thenReturn(events);
        when(eventMapper.toDTO(mockEvent)).thenReturn(mockEventDTO);

        List<EventDTO> result = eventService.getAllEvents();

        assertNotNull(result);
        assertEquals(eventDTOs, result);
        verify(eventRepository, times(1)).findAll();
        verify(eventMapper, times(1)).toDTO(mockEvent);
    }

    @Test
    public void deleteEventById_ValidId_ReturnsSuccessMessage() {
        Long eventId = 1L;
        when(eventServiceUtils.findById(eventId)).thenReturn(mockEvent);

        String result = eventService.deleteEventById(eventId);

        assertEquals("Event deleted successfully.", result);
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    public void deleteEventById_NonExistingEvent_ThrowsException() {
        Long eventId = 99L;
        when(eventServiceUtils.findById(eventId)).thenReturn(null);

        Exception exception = assertThrows(NullPointerException.class,
                () -> eventService.deleteEventById(eventId));

        assertNotNull(exception);
        verify(eventRepository, never()).deleteById(eventId);
    }
}
