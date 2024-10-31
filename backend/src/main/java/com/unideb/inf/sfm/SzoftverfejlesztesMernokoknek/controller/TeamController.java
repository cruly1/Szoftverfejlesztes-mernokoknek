package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto.TeamDTO;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.mapper.TeamMapper;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/teams/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMapper teamMapper;

    @GetMapping("{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("id") Long teamId) {
        TeamDTO teamDTO = teamService.getTeamById(teamId);
        return ResponseEntity.ok(teamDTO);
    }

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

    @PutMapping("{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable("id") Long teamId, @RequestBody Team updatedTeam) {
        Team team = teamService.updateTeam(teamId, updatedTeam);
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
