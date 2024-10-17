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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Player player = new Player();
        player.setFirstName("Alex");
        player.setLastName("Pasch");
        player.setDateOfBirth(LocalDate.of(2001, 8, 13));
        player.setTeamId(null);

        // mock
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));

        // act
        ResponseEntity<Player> response = playerController.getPlayerById(1L);

        // assert: verify the result and interaction
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    public void testGetAllPlayers_OnlyOnePlayerAdded() {
        // arrange
        List<Player> players = List.of(new Player(2L, "Kupecz", "Levente", LocalDate.of(2001, 1, 14), 10L));

        // mock
        when(playerService.getAllPlayers()).thenReturn(players);

        // act
        ResponseEntity<List<Player>> controllerPlayers = playerController.getAllPlayers();

        // verify
        assertEquals(players, controllerPlayers.getBody());
        verify(playerService, times(1)).getAllPlayers();
    }
}
