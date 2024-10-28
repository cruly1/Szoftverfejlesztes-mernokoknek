package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controllertest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller.PlayerController;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.EGender;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

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
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setFirstName("Laci");
        playerDTO.setLastName("Kiss");
        playerDTO.setTeamName("Liquid");

        // mock
        when(playerService.getPlayerById(1L)).thenReturn(playerDTO);

        // act
        ResponseEntity<PlayerDTO> response = playerController.getPlayerById(1L);

        // verify
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(playerDTO, response.getBody());
        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    public void testGetPlayerById_Failed() {
        // arrange
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setFirstName("Lajos");
        playerDTO.setLastName("Nagy");
        playerDTO.setTeamName("Liquid");

        // mock
        when(playerService.getPlayerById(1L)).thenReturn(playerDTO);

        // act
        ResponseEntity<PlayerDTO> response = playerController.getPlayerById(20L);

        // verify
        if (response == null) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
        Assertions.assertNotEquals(playerDTO, response.getBody());
        verify(playerService, times(0)).getPlayerById(1L);
    }

    @Test
    public void testGetAllPlayers_OnlyOnePlayerAdded() {
        // arrange
        List<PlayerDTO> playersDTO = List.of(new PlayerDTO(
                "Lajos",
                "Balazs",
                "xxxBazsikaxxx",
                LocalDate.of(2005, 10, 10),
                "Liquid",
                EGender.MALE,
                "hungarian"
                ));

        // mock
        when(playerService.getAllPlayers()).thenReturn(playersDTO);

        // act
        ResponseEntity<List<PlayerDTO>> controllerPlayers = playerController.getAllPlayers();

        // verify
        Assertions.assertEquals(playersDTO, controllerPlayers.getBody());
        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    public void testCreatePlayer_Successful() {
        // arrange
        Player player = new Player();
        player.setFirstName("Pista");
        player.setLastName("Hegyi");
        player.setDateOfBirth(LocalDate.of(2007, 4, 3));

        // mock
        when(playerService.addPlayer(any())).thenReturn(player);

        // act
        ResponseEntity<Player>response = playerController.createPlayer(player);

        // verify
        Assertions.assertEquals(player, response.getBody());
        verify(playerService, times(1)).addPlayer(any());
    }

    @Test
    public void testUpdatePlayer_Successful() {
        // arrange
        Long playerId = 1L;
        Player updatedPlayer=new Player();
        updatedPlayer.setFirstName("István");
        updatedPlayer.setLastName("Kovács");
        updatedPlayer.setDateOfBirth(LocalDate.of(2003, 3, 12));

        Player returnedPlayer = new Player();
        returnedPlayer.setId(playerId);
        returnedPlayer.setFirstName("István");
        returnedPlayer.setLastName("Kovács");
        returnedPlayer.setDateOfBirth(LocalDate.of(2003, 3, 12));

        // mock
        when(playerService.updatePlayer(eq(playerId), any(Player.class))).thenReturn(returnedPlayer);

        // act
        ResponseEntity<Player> response = playerController.updatePlayer(playerId, updatedPlayer);

        // assert
        Assertions.assertEquals(returnedPlayer, response.getBody());
        verify(playerService, times(1)).updatePlayer(eq(playerId), any(Player.class));
    }

    @Test
    public void testDeletePlayer_Successful() {
        // arrange
        Long playerId=1L;
        String expectedResponse="Player deleted successfully.";

        // mock
        when(playerService.deletePlayerById(playerId)).thenReturn(expectedResponse);

        // act
        ResponseEntity<String>response = playerController.deletePlayer(playerId);

        // assert
        Assertions.assertEquals(expectedResponse, response.getBody());
        verify(playerService, times(1)).deletePlayerById(playerId);
    }

    @Test
    public void testDeletePlayer_Failed() {
        // arrange
        Long playerId = 2L;

        // mock
        when(playerService.deletePlayerById(playerId)).thenReturn(null);

        // act
        ResponseEntity<String> response = playerController.deletePlayer(playerId);

        // assert
        Assertions.assertNull(response.getBody());
        verify(playerService, times(1)).deletePlayerById(playerId);
    }
}
