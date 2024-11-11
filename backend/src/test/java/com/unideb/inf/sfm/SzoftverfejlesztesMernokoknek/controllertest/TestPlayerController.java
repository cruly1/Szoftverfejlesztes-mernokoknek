package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controllertest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller.PlayerController;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.PlayerMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;

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

public class TestPlayerController {

    @Mock
    private PlayerService playerService;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetPlayerById() {
//        Long playerId = 1L;
//        PlayerDTO playerDTO = new PlayerDTO();
//        when(playerService.getPlayerById(playerId)).thenReturn(playerDTO);
//
//        ResponseEntity<PlayerDTO> response = playerController.getPlayerById(playerId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(playerDTO, response.getBody());
//    }

    @Test
    public void testGetPlayerByNickName() {
        String nickName = "testNickname";
        PlayerDTO playerDTO = new PlayerDTO();
        when(playerService.getPlayerByNickName(nickName)).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> response = playerController.getPlayerByNickName(nickName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playerDTO, response.getBody());
    }

    @Test
    public void testGetPlayerByUsername() {
        String username = "testUsername";
        PlayerDTO playerDTO = new PlayerDTO();
        when(playerService.getPlayerByUsername(username)).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> response = playerController.getPlayerByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playerDTO, response.getBody());
    }

//    @Test
//    public void testAddPlayerToEvent() {
//        Long playerId = 1L;
//        Long eventId = 1L;
//        String expectedResponse = "Player added to event";
//        when(playerService.addPlayerToEvent(playerId, eventId)).thenReturn(expectedResponse);
//
//        String response = playerController.addPlayerToEvent(playerId, eventId);
//
//        assertEquals(expectedResponse, response);
//    }

    @Test
    public void testCreatePlayer() {
        Player player = new Player();
        Player savedPlayer = new Player();
        String username = "testUsername";
        PlayerDTO playerDTO = new PlayerDTO();
        when(playerService.addPlayer(player, username)).thenReturn(savedPlayer);
        when(playerMapper.toDTO(savedPlayer)).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> response = playerController.createPlayer(player, username);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(playerDTO, response.getBody());
    }

    @Test
    public void testUpdatePlayer() {
        String nickName = "testNickname";
        Player updatedPlayer = new Player();
        Player savedPlayer = new Player();
        PlayerDTO playerDTO = new PlayerDTO();
        when(playerService.updatePlayer(nickName, updatedPlayer)).thenReturn(savedPlayer);
        when(playerMapper.toDTO(savedPlayer)).thenReturn(playerDTO);

        ResponseEntity<PlayerDTO> response = playerController.updatePlayer(nickName, updatedPlayer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playerDTO, response.getBody());
    }

    @Test
    public void testDeletePlayer() {
        Long playerId = 1L;
        String expectedResponse = "Player deleted";
        when(playerService.deletePlayerById(playerId)).thenReturn(expectedResponse);

        ResponseEntity<String> response = playerController.deletePlayer(playerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetAllPlayers() {
        PlayerDTO playerDTO = new PlayerDTO();
        List<PlayerDTO> playerDTOList = Collections.singletonList(playerDTO);
        when(playerService.getAllPlayers()).thenReturn(playerDTOList);

        ResponseEntity<List<PlayerDTO>> response = playerController.getAllPlayers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playerDTOList, response.getBody());
    }
}
