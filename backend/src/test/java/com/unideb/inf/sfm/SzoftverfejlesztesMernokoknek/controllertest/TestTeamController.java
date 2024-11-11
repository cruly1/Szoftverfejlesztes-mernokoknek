package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controllertest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller.TeamController;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.TeamService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestTeamController {

    @Mock
    private TeamService teamService;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetTeamById() {
//        Long teamId = 1L;
//        TeamDTO teamDTO = new TeamDTO();
//        when(teamService.getTeamById(teamId)).thenReturn(teamDTO);
//
//        ResponseEntity<TeamDTO> response = teamController.getTeamById(teamId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(teamDTO, response.getBody());
//    }

    @Test
    public void testCreateTeam() {
        Team team = new Team();
        Team savedTeam = new Team();
        TeamDTO teamDTO = new TeamDTO();
        when(teamService.addTeam(team)).thenReturn(savedTeam);
        when(teamMapper.toDTO(savedTeam)).thenReturn(teamDTO);

        ResponseEntity<TeamDTO> response = teamController.createTeam(team);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(teamDTO, response.getBody());
    }

//    @Test
//    public void testUpdateTeam() {
//        Long teamId = 1L;  // Adjust to match the actual method's parameter type
//        Team updatedTeam = new Team();
//        Team savedTeam = new Team();
//        TeamDTO teamDTO = new TeamDTO();
//        when(teamService.updateTeam(teamId, updatedTeam)).thenReturn(savedTeam); // Adjust to match method signature
//        when(teamMapper.toDTO(savedTeam)).thenReturn(teamDTO);
//
//        ResponseEntity<TeamDTO> response = teamController.updateTeam(teamId, updatedTeam); // Adjust to match method signature
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(teamDTO, response.getBody());
//    }

    @Test
    public void testDeleteTeam() {
        Long teamId = 1L;
        String expectedResponse = "Team deleted";
        when(teamService.deleteTeamById(teamId)).thenReturn(expectedResponse);

        ResponseEntity<String> response = teamController.deleteTeam(teamId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetAllTeams() {
        TeamDTO teamDTO = new TeamDTO();
        List<TeamDTO> teamDTOList = Collections.singletonList(teamDTO);
        when(teamService.getAllTeams()).thenReturn(teamDTOList);

        ResponseEntity<List<TeamDTO>> response = teamController.getAllTeams();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teamDTOList, response.getBody());
    }
}
