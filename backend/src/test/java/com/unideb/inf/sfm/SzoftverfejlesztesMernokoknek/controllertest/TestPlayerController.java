package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controllertest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller.PlayerController;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestPlayerController {

    @Mock
    PlayerService playerService;

    @InjectMocks
    PlayerController playerController;

    @Test
    public void testGetPlayerById_Successful() {
        // arrange
        Player player=new Player();
        player.setFirstName("Laci");
        player.setLastName("Kiss");
        player.setDateOfBirth(LocalDate.of(2001, 8, 13));

        // mock
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        // act
        ResponseEntity<Player>response=playerController.getPlayerById(1L);

        // assert: verify the result and interaction
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
        verify(playerService, times(1)).getPlayerById(1L);
    }

    //getPlayerById
    @Test
    public void testGetPlayerById_Failed() {
        // arrange
        Player player=new Player();
        player.setFirstName("Lajos");
        player.setLastName("Nagy");
        player.setDateOfBirth(LocalDate.of(2004, 5, 8));

        // mock
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        // act
        ResponseEntity<Player>response=playerController.getPlayerById(2L);

        // assert: verify the result and interaction
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotEquals(player, response.getBody());
        verify(playerService, times(0)).getPlayerById(1L);
    }

    //getAllPlayers
    @Test
    public void testGetAllPlayers_OnlyOnePlayerAdded() {
        // arrange
        List<Player>players=List.of(new Player(2L, "Kupecz", "Levente", LocalDate.of(2001, 1, 14)));

        // mock
        when(playerService.getAllPlayers()).thenReturn(players);

        // act
        ResponseEntity<List<Player>>controllerPlayers=playerController.getAllPlayers();

        // verify
        assertEquals(players, controllerPlayers.getBody());
        verify(playerService, times(1)).getAllPlayers();
    }

    //createPlayer
    @Test
    public void testCreatePlayer_Successful() {
        // arrange
        Player player=new Player();
        player.setFirstName("Pista");
        player.setLastName("Hegyi");
        player.setDateOfBirth(LocalDate.of(2007, 4, 3));

        // mock
        when(playerService.addPlayer(any())).thenReturn(player);

        // act
        ResponseEntity<Player>response=playerController.createPlayer(player);

        // verify
        assertEquals(player, response.getBody());
        verify(playerService, times(1)).addPlayer(any());
    }

    //updatePlayer
    @Test
    public void testUpdatePlayer_Successful() {
        // arrange
        Long playerId=1L;
        Player updatedPlayer=new Player();
        updatedPlayer.setFirstName("István");
        updatedPlayer.setLastName("Kovács");
        updatedPlayer.setDateOfBirth(LocalDate.of(2003, 3, 12));

        Player returnedPlayer=new Player();
        returnedPlayer.setId(playerId);
        returnedPlayer.setFirstName("István");
        returnedPlayer.setLastName("Kovács");
        returnedPlayer.setDateOfBirth(LocalDate.of(2003, 3, 12));

        // mock
        when(playerService.updatePlayer(eq(playerId), any(Player.class))).thenReturn(returnedPlayer);

        // act
        ResponseEntity<Player>response=playerController.updatePlayer(playerId, updatedPlayer);

        // assert
        assertEquals(returnedPlayer, response.getBody());
        verify(playerService, times(1)).updatePlayer(eq(playerId), any(Player.class));
    }

    //deletePlayer
    @Test
    public void testDeletePlayer_Successful() {
        // arrange
        Long playerId=1L;
        String expectedResponse="Player deleted successfully.";

        // mock
        when(playerService.deletePlayerById(playerId)).thenReturn(expectedResponse);

        // act
        ResponseEntity<String>response=playerController.deletePlayer(playerId);

        // assert
        assertEquals(expectedResponse, response.getBody());
        verify(playerService, times(1)).deletePlayerById(playerId);
    }

    @Test
    public void testDeletePlayer_Failed() {
        // arrange
        Long playerId=2L;

        // mock
        when(playerService.deletePlayerById(playerId)).thenReturn(null);

        // act
        ResponseEntity<String>response=playerController.deletePlayer(playerId);

        // assert
        assertNull(response.getBody());
        verify(playerService, times(1)).deletePlayerById(playerId);
    }
}