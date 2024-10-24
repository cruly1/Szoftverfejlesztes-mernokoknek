package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("id") Long playerId) {
        PlayerDTO playerDTO = playerService.getPlayerById(playerId);
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
        List<PlayerDTO> playerDTOS = playerService.getAllPlayers();
        return ResponseEntity.ok(playerDTOS);
    }
}
