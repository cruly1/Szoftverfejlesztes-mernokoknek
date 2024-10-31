package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.DuplicateRoleException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.TeamLimitExceededException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.PlayerMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.InvalidParametersException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PlayerMapper playerMapper;

    public PlayerDTO getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(playerId, "Player"));

        return playerMapper.toDTO(player);
    }

    public PlayerDTO getPlayerByNickName(String nickName) {
        Player player = playerRepository.findByNickName(nickName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Player"));

        return playerMapper.toDTO(player);
    }

    public Player addPlayer(Player player) {
        Team team = player.getTeam() != null ?
                teamRepository.findByTeamName(player.getTeam().getTeamName())
                        .orElseThrow(() -> new ResourceNotFoundException(-1L, "Team"))
                : null;

        if (team != null) {
            if (team.getPlayersInTeam().size() >= 6) {
                throw new TeamLimitExceededException("Only 6 members are allowed to join each team.");
            }

            EIngameRoles role = player.getIngameRole();
            boolean isRoleTaken = team.getPlayersInTeam().stream()
                    .anyMatch(existingPlayer -> existingPlayer.getIngameRole().equals(role));

            if (isRoleTaken) {
                throw new DuplicateRoleException("This role is already taken.");
            }
        }

        try {
            player.setTeam(team);
            return playerRepository.save(player);
        } catch (RuntimeException e) {
            throw new InvalidParametersException();
        }
    }

    public String addPlayerToEvent(Long playerId, Long eventId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Player"));

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Event"));

        try {
            event.getPlayers().add(player);
            player.getEvents().add(event);
            playerRepository.save(player);
            eventRepository.save(event);
            return "Player added to event successfully.";
        } catch (RuntimeException e) {
            throw new InvalidParametersException();
        }
    }

    public Player updatePlayer(Long playerId, Player updatedPlayer) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(playerId, "Player"));

        BeanUtils.copyProperties(updatedPlayer, player, "id");

        Team team = teamRepository.findByTeamName(player.getTeam().getTeamName())
                .orElseThrow(() -> new ResourceNotFoundException(-1L, "Team"));

        if (team.getPlayersInTeam().size() >= 6) {
            throw new TeamLimitExceededException("Only 6 members are allowed to join each team.");
        }

        EIngameRoles role = player.getIngameRole();
        boolean isRoleTaken = team.getPlayersInTeam().stream()
                .filter(existingPlayer -> !existingPlayer.getId().equals(playerId))
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

    public String deletePlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException(playerId, "Player"));

        playerRepository.deleteById(playerId);
        return "Player deleted successfully.";
    }

    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .map(playerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
