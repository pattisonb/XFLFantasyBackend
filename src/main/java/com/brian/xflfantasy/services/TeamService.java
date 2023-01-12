package com.brian.xflfantasy.services;

import com.brian.xflfantasy.models.Team;
import com.brian.xflfantasy.repositories.TeamRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {

    @Autowired
    private final TeamRepo teamRepo;

    public Team createTeam(Team team){
        return teamRepo.save(team);
    }

    public void deleteTeamById(int teamId){
        teamRepo.deleteById(teamId);
    }

    public Team updateTeamById(int teamId, Team team){
        team.setId(teamId);
        return teamRepo.save(team);
    }

    public List<Team> getAllTeams(){
        return teamRepo.findAll();
    }
    public Team getTeamById(int teamId){
        return teamRepo.getReferenceById(teamId);
    }
}
