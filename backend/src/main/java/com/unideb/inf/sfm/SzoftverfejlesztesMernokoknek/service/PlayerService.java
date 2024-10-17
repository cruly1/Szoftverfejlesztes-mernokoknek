package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.PlayerNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Optional<Player> getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new PlayerNotFoundException(playerId));

        return Optional.of(player);
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Long playerId, Player updatedPlayer) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new PlayerNotFoundException(playerId));

        player.setFirstName(updatedPlayer.getFirstName());
        player.setLastName(updatedPlayer.getLastName());
        player.setDateOfBirth(updatedPlayer.getDateOfBirth());
        player.setTeamId(updatedPlayer.getTeamId());

        return playerRepository.save(player);
    }

    public String deletePlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new PlayerNotFoundException(playerId));

        playerRepository.deleteById(playerId);
        return "Player deleted successfully.";
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
