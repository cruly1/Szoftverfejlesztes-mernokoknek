package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.PlayerDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("id") Long teamId) {
        Optional<Team> team = teamService.getTeamById(teamId);

        List<String> players = new ArrayList<>();

        for (Player player : team.get().getPlayers()) {
            players.add(player.getFirstName());
        }

        TeamDTO teamDTO = new TeamDTO(
                team.get().getId(),
                team.get().getTeamName(),
                players
        );

        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team savedTeam = teamService.addTeam(team);
        return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") Long teamId, @RequestBody Team updatedTeam) {
        Team team = teamService.updateTeam(teamId, updatedTeam);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable("id") Long teamId) {
        String response = teamService.deleteTeamById(teamId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllTeams")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();

        List<TeamDTO> teamDTOS = new ArrayList<>();

        List<String> players = new ArrayList<>();

        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                players.add(player.getFirstName());
            }

            teamDTOS.add(new TeamDTO(
                    team.getId(),
                    team.getTeamName(),
                    players
            ));
        }

        return ResponseEntity.ok(teamDTOS);
    }
}
