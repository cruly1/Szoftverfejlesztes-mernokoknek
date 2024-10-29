package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMapper teamMapper;

    public TeamDTO getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(teamId, "Team"));

        return teamMapper.toDTO(team);
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long teamId, Team updatedTeam) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(teamId, "Team"));

        team.setTeamName(updatedTeam.getTeamName());

        return teamRepository.save(team);
    }

    public String deleteTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(teamId, "Team"));

        teamRepository.deleteById(teamId);
        return "Team deleted successfully.";
    }

    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> teamDTOS = new ArrayList<>();

        teams.forEach(team -> teamDTOS.add(teamMapper.toDTO(team)));

        return teamDTOS;
    }
}
