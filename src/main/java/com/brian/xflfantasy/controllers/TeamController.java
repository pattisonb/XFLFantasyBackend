package com.brian.xflfantasy.controllers;


import com.brian.xflfantasy.models.Team;
import com.brian.xflfantasy.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/teams")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamController {

    @Autowired
    private final TeamService teamService;

    @PostMapping
    public Team createTeam(@RequestBody Team team){
        return teamService.createTeam(team);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") int id){
        teamService.deleteTeamById(id);
    }

    @PatchMapping("/{id}")
    public Team updateTeam(@PathVariable("id") int id, @RequestBody Team team){
        return teamService.updateTeamById(id, team);
    }

    @GetMapping
    public List<Team> getTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable("id") int id){
        return teamService.getTeamById(id);
    }
}
