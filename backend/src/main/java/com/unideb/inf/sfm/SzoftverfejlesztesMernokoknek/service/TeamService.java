package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.InvalidParametersException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.TeamAlreadyExistsException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
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
    private EventRepository eventRepository;

    @Autowired
    private TeamMapper teamMapper;

    public TeamDTO getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(teamId, "Team"));

        return teamMapper.toDTO(team);
    }

    public TeamDTO getTeamByTeamName(String teamName) {
        Team team = teamRepository.findByTeamName(teamName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Team"));

        return teamMapper.toDTO(team);
    }

    public Team addTeam(Team team) {
        if (teamRepository.findByTeamName(team.getTeamName()).isPresent()) {
            throw new TeamAlreadyExistsException("Team already exists.");
        }

        return teamRepository.save(team);
    }

    public String addTeamToEvent(Long teamId, Long eventId) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Team"));

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Event"));

        try {
            event.getTeams().add(team);
            team.getEvents().add(event);
            teamRepository.save(team);
            eventRepository.save(event);
            return "Team added to event successfully.";
        } catch (RuntimeException e) {
            throw new InvalidParametersException();
        }
    }

    public Team updateTeam(Long teamId, Team updatedTeam) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(teamId, "Team"));

        teamRepository.findByTeamName(updatedTeam.getTeamName())
                .filter(existingTeam -> !existingTeam.getId().equals(teamId))
                .ifPresent(existingTeam -> {
                    throw new TeamAlreadyExistsException("Team already exists.");
        });

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
