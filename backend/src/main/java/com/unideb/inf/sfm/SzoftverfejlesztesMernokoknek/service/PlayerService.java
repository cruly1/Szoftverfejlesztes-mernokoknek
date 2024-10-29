package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.DuplicateRoleException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.TeamLimitExceededException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.InvalidParametersException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    public PlayerDTO getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(playerId, "Player"));

        return new PlayerDTO(
                player.getFirstName(),
                player.getLastName(),
                player.getNickName(),
                player.getIngameRole(),
                player.getDateOfBirth(),
                player.getTeam().getTeamName(),
                player.getGender(),
                player.getNationality()
        );
    }

    public PlayerDTO getPlayerByNickName(String nickName) {
        Player player = playerRepository.findByNickName(nickName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Player"));

        return new PlayerDTO(
                player.getFirstName(),
                player.getLastName(),
                player.getNickName(),
                player.getIngameRole(),
                player.getDateOfBirth(),
                player.getTeam().getTeamName(),
                player.getGender(),
                player.getNationality()
        );
    }

    public Player addPlayer(Player player) {
        Team team = teamRepository.findById(player.getTeam().getId())
                .orElseThrow(() -> new ResourceNotFoundException(-1L, "Team"));

        if (team.getPlayersInTeam().size() >= 6) {
            throw new TeamLimitExceededException("Only 6 members are allowed to join each team.");
        }

        EIngameRoles role = player.getIngameRole();
        boolean isRoleTaken = team.getPlayersInTeam().stream()
                .anyMatch(existingPlayer -> existingPlayer.getIngameRole().equals(role));

        if (isRoleTaken) {
            throw new DuplicateRoleException("This role is already taken.");
        }

        try {
            player.setTeam(team);
            return playerRepository.save(player);
        } catch (RuntimeException e) {
            throw new InvalidParametersException();
        }
    }

    public Player updatePlayer(Long playerId, Player updatedPlayer) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(playerId, "Player"));

        player.setFirstName(updatedPlayer.getFirstName());
        player.setLastName(updatedPlayer.getLastName());
        player.setDateOfBirth(updatedPlayer.getDateOfBirth());

        return playerRepository.save(player);
    }

    public String deletePlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(playerId, "Player"));

        playerRepository.deleteById(playerId);
        return "Player deleted successfully.";
    }

    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDTO> playerDTOS = new ArrayList<>();

        players.stream().map((player -> new PlayerDTO(
                        player.getFirstName(),
                        player.getLastName(),
                        player.getNickName(),
                        player.getIngameRole(),
                        player.getDateOfBirth(),
                        player.getTeam().getTeamName(),
                        player.getGender(),
                        player.getNationality())))
                .forEach(playerDTOS::add);

        return playerDTOS;
    }
}
