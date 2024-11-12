package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.EventRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.TeamRepository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.EventServiceUtils;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.TeamServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;
    private final TeamMapper teamMapper;
    private final TeamServiceUtils teamServiceUtils;
    private final EventServiceUtils eventServiceUtils;

    public TeamDTO getTeamByTeamName(String teamName) {
        Team team = teamServiceUtils.findByTeamName(teamName);
        return teamMapper.toDTO(team);
    }

    public Team addTeam(Team team) {
        if (teamServiceUtils.teamAlreadyExists(team)) {
            return null;
        }
        return teamRepository.save(team);
    }

    public String addTeamToEvent(String teamName, String eventName) {
        Team team = teamServiceUtils.findByTeamName(teamName);
        Event event = eventServiceUtils.findByEventName(eventName);

        event.getTeams().add(team);
        team.getEvents().add(event);
        teamRepository.save(team);
        eventRepository.save(event);

        return "Team added to event successfully.";
    }

    public Team updateTeam(String teamName, Team updatedTeam) {
        Team team = teamServiceUtils.findByTeamName(teamName);

        teamServiceUtils.teamAlreadyExists(team);
        team.setTeamName(updatedTeam.getTeamName());

        return teamRepository.save(team);
    }

    public String deleteTeamById(Long teamId) {
        Team team = teamServiceUtils.findById(teamId);

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
