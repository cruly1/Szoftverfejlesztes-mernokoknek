package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("id") Long playerId) {
        Optional<Player> player = playerService.getPlayerById(playerId);

        PlayerDTO playerDTO = new PlayerDTO(
                player.get().getId(),
                player.get().getFirstName(),
                player.get().getLastName(),
                player.get().getTeam().getTeamName()
        );

        return ResponseEntity.ok(playerDTO);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long playerId, @RequestBody Player updatedPlayer) {
        Player player = playerService.updatePlayer(playerId, updatedPlayer);
        return ResponseEntity.ok(player);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id") Long id) {
        String response = playerService.deletePlayerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllPlayers")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();

        List<PlayerDTO> playerDTOS = new ArrayList<>();

        players.stream().map((player -> new PlayerDTO(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getTeam().getTeamName())))
                .forEach(playerDTOS::add);

        return ResponseEntity.ok(playerDTOS);
    }
}
