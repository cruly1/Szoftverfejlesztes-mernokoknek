package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.TeamService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/teams/")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping(value = "search")
    public ResponseEntity<TeamDTO> getTeamByTeamName(@RequestParam("teamName") String teamName) {
        TeamDTO teamDTO = teamService.getTeamByTeamName(teamName);
        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody Team team) {
        Team savedTeam = teamService.addTeam(team);
        return new ResponseEntity<>(teamMapper.toDTO(savedTeam), HttpStatus.CREATED);
    }

    @PutMapping("addToEvent/search")
    public String addTeamToEvent(@RequestParam("teamName") String teamName, @RequestParam("eventName") String eventName) {
        return teamService.addTeamToEvent(teamName, eventName);
    }

    @PutMapping(value = "search")
    public ResponseEntity<TeamDTO> updateTeam(@RequestParam("teamName") String teamName, @RequestBody Team updatedTeam) {
        Team team = teamService.updateTeam(teamName, updatedTeam);
        return ResponseEntity.ok(teamMapper.toDTO(team));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable("id") Long teamId) {
        String response = teamService.deleteTeamById(teamId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getAllTeams")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teamDTOS = teamService.getAllTeams();
        return ResponseEntity.ok(teamDTOS);
    }
}
