package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.PlayerMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/players/")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerMapper playerMapper;

    @GetMapping("{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable("id") Long playerId) {
        PlayerDTO playerDTO = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(playerDTO);
    }

    @GetMapping(value = "search")
    public ResponseEntity<PlayerDTO> getPlayerByNickName(@RequestParam("nickName") String nickName) {
        PlayerDTO playerDTO = playerService.getPlayerByNickName(nickName);
        return ResponseEntity.ok(playerDTO);
    }

    @PostMapping("{playerId}/events/{eventId}")
    public String addPlayerToEvent(@PathVariable("playerId") Long playerId, @PathVariable("eventId") Long eventId) {
        return playerService.addPlayerToEvent(playerId, eventId);
    }

    @PostMapping("addPlayer/search")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody Player player, @RequestParam("username") String username) {
        Player savedPlayer = playerService.addPlayer(player, username);
        return new ResponseEntity<>(playerMapper.toDTO(savedPlayer), HttpStatus.CREATED);
    }

    @PutMapping("updatePlayer/search")
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestParam("nickName") String nickName, @RequestBody Player updatedPlayer) {
        Player player = playerService.updatePlayer(nickName, updatedPlayer);
        return ResponseEntity.ok(playerMapper.toDTO(player));
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
