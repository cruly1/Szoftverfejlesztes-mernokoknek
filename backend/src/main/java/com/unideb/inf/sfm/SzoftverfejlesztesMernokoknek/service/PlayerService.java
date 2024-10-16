package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    // TODO: custom exception for not finding player
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player player = playerRepository.findById(id).orElseThrow();

        player.setFirstName(updatedPlayer.getFirstName());
        player.setLastName(updatedPlayer.getLastName());
        player.setDateOfBirth(updatedPlayer.getDateOfBirth());
        player.setTeamId(updatedPlayer.getTeamId());

        return playerRepository.save(player);
    }

    public String deletePlayerById(Long userId) {
        playerRepository.deleteById(userId);
        return "Player deleted successfully.";
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
