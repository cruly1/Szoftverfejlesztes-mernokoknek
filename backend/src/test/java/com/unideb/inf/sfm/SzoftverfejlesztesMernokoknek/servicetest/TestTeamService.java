package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.servicetest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.TeamService;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.EventServiceUtils;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.TeamServiceUtils;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet; // Ezt add hozzá
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestTeamService {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TeamMapper teamMapper;

    @Mock
    private TeamServiceUtils teamServiceUtils;

    @Mock
    private EventServiceUtils eventServiceUtils;

    @InjectMocks
    private TeamService teamService;

    private Team mockTeam;
    private TeamDTO mockTeamDTO;
    private Event mockEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockTeam = new Team();
        mockTeam.setId(1L);
        mockTeam.setTeamName("TestTeam");
        mockTeam.setEvents(new HashSet<>()); // HashSet használata

        mockTeamDTO = new TeamDTO();
        mockTeamDTO.setTeamName("TestTeam");

        mockEvent = new Event();
        mockEvent.setEventName("TestEvent");
        mockEvent.setTeams(new HashSet<>()); // HashSet használata
    }

    @Test
    public void getTeamByTeamName_ExistingTeam_ReturnsTeamDTO() {
        String teamName = "TestTeam";
        when(teamServiceUtils.findByTeamName(teamName)).thenReturn(mockTeam);
        when(teamMapper.toDTO(mockTeam)).thenReturn(mockTeamDTO);

        TeamDTO result = teamService.getTeamByTeamName(teamName);

        assertNotNull(result);
        assertEquals(mockTeamDTO, result);
        verify(teamServiceUtils, times(1)).findByTeamName(teamName);
        verify(teamMapper, times(1)).toDTO(mockTeam);
    }

    @Test
    public void addTeam_NewTeam_ReturnsSavedTeam() {
        when(teamServiceUtils.teamAlreadyExists(mockTeam)).thenReturn(false);
        when(teamRepository.save(mockTeam)).thenReturn(mockTeam);

        Team result = teamService.addTeam(mockTeam);

        assertNotNull(result);
        assertEquals(mockTeam, result);
        verify(teamServiceUtils, times(1)).teamAlreadyExists(mockTeam);
        verify(teamRepository, times(1)).save(mockTeam);
    }

    @Test
    public void addTeam_ExistingTeam_ReturnsNull() {
        when(teamServiceUtils.teamAlreadyExists(mockTeam)).thenReturn(true);

        Team result = teamService.addTeam(mockTeam);

        assertNull(result);
        verify(teamServiceUtils, times(1)).teamAlreadyExists(mockTeam);
        verify(teamRepository, never()).save(mockTeam);
    }

    @Test
    public void addTeamToEvent_ValidInputs_ReturnsSuccessMessage() {
        String teamName = "TestTeam";
        String eventName = "TestEvent";

        when(teamServiceUtils.findByTeamName(teamName)).thenReturn(mockTeam);
        when(eventServiceUtils.findByEventName(eventName)).thenReturn(mockEvent);
        when(teamRepository.save(mockTeam)).thenReturn(mockTeam);
        when(eventRepository.save(mockEvent)).thenReturn(mockEvent);

        String result = teamService.addTeamToEvent(teamName, eventName);

        assertEquals("Team added to event successfully.", result);
        assertTrue(mockEvent.getTeams().contains(mockTeam));
        assertTrue(mockTeam.getEvents().contains(mockEvent));
        verify(teamServiceUtils, times(1)).findByTeamName(teamName);
        verify(eventServiceUtils, times(1)).findByEventName(eventName);
        verify(teamRepository, times(1)).save(mockTeam);
        verify(eventRepository, times(1)).save(mockEvent);
    }

    @Test
    public void updateTeam_ValidUpdate_ReturnsUpdatedTeam() {
        String teamName = "OldTeam";
        Team updatedTeam = new Team();
        updatedTeam.setTeamName("NewTeam");

        when(teamServiceUtils.findByTeamName(teamName)).thenReturn(mockTeam);
        when(teamRepository.save(mockTeam)).thenReturn(mockTeam);

        Team result = teamService.updateTeam(teamName, updatedTeam);

        assertNotNull(result);
        assertEquals("NewTeam", result.getTeamName());
        verify(teamServiceUtils, times(1)).findByTeamName(teamName);
        verify(teamRepository, times(1)).save(mockTeam);
    }

    @Test
    public void deleteTeamById_ValidId_ReturnsSuccessMessage() {
        Long teamId = 1L;
        when(teamServiceUtils.findById(teamId)).thenReturn(mockTeam);

        String result = teamService.deleteTeamById(teamId);

        assertEquals("Team deleted successfully.", result);
        verify(teamRepository, times(1)).deleteById(teamId);
    }

    @Test
    public void getAllTeams_ReturnsTeamDTOList() {
        List<Team> teams = List.of(mockTeam);
        List<TeamDTO> teamDTOs = List.of(mockTeamDTO);

        when(teamRepository.findAll()).thenReturn(teams);
        when(teamMapper.toDTO(mockTeam)).thenReturn(mockTeamDTO);

        List<TeamDTO> result = teamService.getAllTeams();

        assertNotNull(result);
        assertEquals(teamDTOs, result);
        verify(teamRepository, times(1)).findAll();
        verify(teamMapper, times(1)).toDTO(mockTeam);
    }

}