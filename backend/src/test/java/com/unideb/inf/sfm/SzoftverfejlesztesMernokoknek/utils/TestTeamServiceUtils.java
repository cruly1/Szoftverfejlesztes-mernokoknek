package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.TeamAlreadyExistsException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TestTeamServiceUtils {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceUtils teamServiceUtils;

    private Team mockTeam;

    @BeforeEach
    void setUp() {
        mockTeam = new Team();
        mockTeam.setId(1L);
        mockTeam.setTeamName("TestTeam");
    }

    @Test
    void findById_TeamExists_ReturnsTeam() {
        // arrange
        when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));

        // act
        Team result = teamServiceUtils.findById(1L);

        // assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void findById_TeamDoesNotExist_ThrowsException() {
        // arrange
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> teamServiceUtils.findById(1L));
        assertEquals("Team not found with id number 1", exception.getMessage());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    void findByTeamName_TeamExists_ReturnsTeam() {
        // arrange
        when(teamRepository.findByTeamName("TestTeam")).thenReturn(Optional.of(mockTeam));

        // act
        Team result = teamServiceUtils.findByTeamName("TestTeam");

        // assert
        assertNotNull(result);
        assertEquals("TestTeam", result.getTeamName());
        verify(teamRepository, times(1)).findByTeamName("TestTeam");
    }

    @Test
    void findByTeamName_TeamDoesNotExist_ThrowsException() {
        // arrange
        when(teamRepository.findByTeamName("TestTeam")).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> teamServiceUtils.findByTeamName("TestTeam"));
        assertEquals("Team not found with id number -1", exception.getMessage());
        verify(teamRepository, times(1)).findByTeamName("TestTeam");
    }

    @Test
    void teamAlreadyExists_TeamExists_ThrowsException() {
        // arrange
        when(teamRepository.findByTeamName("TestTeam")).thenReturn(Optional.of(mockTeam));

        // act & assert
        TeamAlreadyExistsException exception = assertThrows(TeamAlreadyExistsException.class, () -> teamServiceUtils.teamAlreadyExists(mockTeam));
        assertEquals("Team already exists.", exception.getMessage());
        verify(teamRepository, times(1)).findByTeamName("TestTeam");
    }

    @Test
    void teamAlreadyExists_TeamDoesNotExist_ReturnsFalse() {
        // arrange
        when(teamRepository.findByTeamName("TestTeam")).thenReturn(Optional.empty());

        // act
        boolean result = teamServiceUtils.teamAlreadyExists(mockTeam);

        // assert
        assertFalse(result);
        verify(teamRepository, times(1)).findByTeamName("TestTeam");
    }
}

