package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long teamId) {
        Optional<Team> team = teamService.getTeamById(teamId);
        return team.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }
}
