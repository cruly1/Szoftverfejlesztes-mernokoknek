package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.PlayerMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.*;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.NationalityRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.AuthServiceUtils;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.EventServiceUtils;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.PlayerServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final EventRepository eventRepository;
    private final PlayerMapper playerMapper;
    private final PlayerServiceUtils playerServiceUtils;
    private final AuthServiceUtils authServiceUtils;
    private final EventServiceUtils eventServiceUtils;
    private final NationalityRepository nationalityRepository;

    // tested
    public PlayerDTO getPlayerByNickName(String nickName) {
        Player player = playerServiceUtils.findByNickName(nickName);
        return playerMapper.toDTO(player);
    }

    // tested
    public PlayerDTO getPlayerByUsername(String username) {
        User user = authServiceUtils.findUserByUsername(username);
        Player player = playerServiceUtils.findByUser(user);
        return playerMapper.toDTO(player);
    }

    // tested
    public Player addPlayer(Player player, String username) {
        User user = authServiceUtils.findUserByUsername(username);
        Team team = playerServiceUtils.getTeamByPlayer(player);

        if (playerServiceUtils.hasRegisteredPlayer(user) ||
                (team != null &&
                        (playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam()) || playerServiceUtils.isRoleTaken(player, team)))) {
            return null;
        }

        Nationality nationality = nationalityRepository.findByDemonym(player.getNationality().getDemonym())
                .orElseThrow(() -> new IllegalArgumentException("Nationality not found"));

        player.setNationality(nationality);
        player.setTeam(team);
        user.setPlayer(player);
        player.setUser(user);
        return playerRepository.save(player);
    }

    // tested
    public String addPlayerToEvent(String nickName, String eventName) {
        Player player = playerServiceUtils.findByNickName(nickName);
        Event event = eventServiceUtils.findByEventName(eventName);

        event.getPlayers().add(player);
        player.getEvents().add(event);
        eventRepository.save(event);
        playerRepository.save(player);

        return "Player added to event successfully.";
    }

    // TODO test
    public Player updatePlayer(String nickName, Player updatedPlayer) {
        Player player = playerServiceUtils.findByNickName(nickName);
        BeanUtils.copyProperties(updatedPlayer, player, "id");
        Team team = playerServiceUtils.getTeamByPlayer(player);

        if (team != null && (playerServiceUtils.isNotAllowedToJoinTeam(team.getPlayersInTeam()) || playerServiceUtils.isRoleTaken(player, team))) {
            return null;
        }

        Nationality nationality = nationalityRepository.findByDemonym(updatedPlayer.getNationality().getDemonym())
                .orElseThrow(() -> new IllegalArgumentException("Nationality not found"));

        player.setNationality(nationality);

        player.setTeam(team);
        return playerRepository.save(player);
    }

    // TODO test
    public String deletePlayerById(Long playerId) {
        Player player = playerServiceUtils.findById(playerId);
        playerRepository.deleteById(playerId);
        return "Player deleted successfully.";
    }

    // TODO test
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .map(playerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
