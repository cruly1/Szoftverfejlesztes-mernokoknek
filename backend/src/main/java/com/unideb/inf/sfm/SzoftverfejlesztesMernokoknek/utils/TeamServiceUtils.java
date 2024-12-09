package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.customexceptions.TeamAlreadyExistsException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceUtils {

    private final TeamRepository teamRepository;

    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id, "Team"));
    }

    public Team findByTeamName(String teamName) {
        return teamRepository.findByTeamName(teamName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Team"));
    }

    public boolean teamAlreadyExists(Team team) {
        if (teamRepository.findByTeamName(team.getTeamName()).isPresent()) {
            throw new TeamAlreadyExistsException("Team already exists.");
        }

        return false;
    }
}
