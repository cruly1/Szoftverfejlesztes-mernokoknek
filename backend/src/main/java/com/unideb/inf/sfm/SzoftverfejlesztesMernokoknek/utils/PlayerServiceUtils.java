package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.DuplicateRoleException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.PlayerAlreadyExistsException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.TeamLimitExceededException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.User;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.enums.EIngameRoles;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceUtils {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    private final static int ALLOWED_TEAM_SIZE = 6;

    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id, "Player"));
    }

    public Player findByNickName(String nickName) {
        return playerRepository.findByNickName(nickName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Player"));
    }

    public Player findByUser(User user) {
        return playerRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Player"));
    }

    public Team getTeamByPlayer(Player player) {
        return player.getTeam() != null ?
                teamRepository.findByTeamName(player.getTeam().getTeamName())
                        .orElseThrow(() -> new ResourceNotFoundException(-1L, "Team"))
                : null;
    }

    public boolean isNotAllowedToJoinTeam(List<Player> players) {
        if (players.size() >= ALLOWED_TEAM_SIZE) {
            throw new TeamLimitExceededException("Only 6 members are allowed to join each team.");
        }

        return true;
    }

    public boolean hasRegisteredPlayer(User user) {
        if (user.getPlayer() != null) {
            throw new PlayerAlreadyExistsException("User already has a player assigned.");
        }

        return false;
    }

    public boolean isRoleTaken(Player player, Team team) {
        EIngameRoles role = player.getIngameRole();
        boolean isRoleTaken = team != null && team.getPlayersInTeam().stream()
                .filter(existingPlayer -> !existingPlayer.getId().equals(player.getId()))
                .anyMatch(existingPlayer -> existingPlayer.getIngameRole().equals(role));

        if (isRoleTaken) {
            throw new DuplicateRoleException("This role is already taken.");
        }
        return false;
    }
}
