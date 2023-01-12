package com.brian.xflfantasy.controllers;


import com.brian.xflfantasy.models.Player;
import com.brian.xflfantasy.services.PlayerService;
import com.brian.xflfantasy.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/players")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlayerController {

    @Autowired
    private final PlayerService playerService;


    @PostMapping
    public Player createPlayer(@RequestBody Player player){
        return playerService.createPlayer(player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable("id") int id){
        playerService.deletePlayerById(id);
    }

    @PatchMapping("/{id}")
    public Player updatePlayer(@PathVariable("id") int id, @RequestBody Player player){
        return playerService.updatePlayerById(id, player);
    }

    @PatchMapping("addPlayer/playerID={playerID}/teamID={teamID}")
    public Player addPlayerToTeam(@PathVariable("playerID") int id, @PathVariable("teamID") int teamID){
        var allPlayers = playerService.getAllPlayers();
        var teamPlayers = new ArrayList<Player>();
        var teamRBS = new ArrayList<Player>();
        var teamWRS = new ArrayList<Player>();
        var teamHasQB = false;
        var teamHasTE = false;
        var teamHasK = false;
        var addedPlayer = playerService.getPlayerById(id);
        for (Player player: allPlayers) {
            if (player.getTeam_id() == teamID) {
                teamPlayers.add((player));
                if (Objects.equals(player.getPosition(), "RB")) {
                    teamRBS.add((player));
                }
                if (Objects.equals(player.getPosition(), "WR")) {
                    teamWRS.add((player));
                }
                if (Objects.equals(player.getPosition(), "TE")) {
                    teamHasTE = true;
                }
            }
        }
        for (Player player: teamPlayers) {
            if (Objects.equals(player.getPosition(), "QB")) {
                teamHasQB = true;
            }
            else if (Objects.equals(player.getPosition(), "K")) {
                teamHasK = true;
            }
        }
        if (teamHasQB && Objects.equals(addedPlayer.getPosition(), "QB")) {
            addedPlayer.setTeam_id(0);
            return playerService.updatePlayerById(id, addedPlayer);
        }
        else if (teamHasK && Objects.equals(addedPlayer.getPosition(), "K")) {
            addedPlayer.setTeam_id(0);
            return playerService.updatePlayerById(id, addedPlayer);
        }
        else if (teamPlayers.size() >= 14) {
            addedPlayer.setTeam_id(0);
            return playerService.updatePlayerById(id, addedPlayer);
        }
        addedPlayer.setTeam_id(teamID);
        if (Objects.equals(addedPlayer.getPosition(), "QB")) {
            addedPlayer.setStarting(true);
        }
        if (Objects.equals(addedPlayer.getPosition(), "K")) {
            addedPlayer.setStarting(true);
        }
        if (Objects.equals(addedPlayer.getPosition(), "RB") && teamRBS.size() < 2) {
            addedPlayer.setStarting(true);
        }
        if (Objects.equals(addedPlayer.getPosition(), "WR") && teamWRS.size() < 2) {
            addedPlayer.setStarting(true);
        }
        if (Objects.equals(addedPlayer.getPosition(), "TE") && !teamHasTE) {
            addedPlayer.setStarting(true);
        }
        return playerService.updatePlayerById(id, addedPlayer);
    }

    @PatchMapping("addPoints/playerID={playerID}/numPoints={numPoints}")
    public Player addPoints(@PathVariable("playerID") int id, @PathVariable("numPoints") double numPoints){
        var player = playerService.getPlayerById(id);
        player.setTotal_points((player.getTotal_points() + numPoints));
        return playerService.updatePlayerById(id, player);
    }

    @PatchMapping("startPlayer/playerToStart={playerToStartID}/playerToBench={playerToBenchID}")
    public Player startPlayer(@PathVariable("playerToStartID") int playerToStartID, @PathVariable("playerToBenchID") int playerToBenchID){
        var playerToStart = playerService.getPlayerById(playerToStartID);
        var playerToBench = playerService.getPlayerById(playerToBenchID);
        playerToStart.setStarting(true);
        playerToBench.setStarting(false);
        playerService.updatePlayerById(playerToBenchID, playerToBench);
        return playerService.updatePlayerById(playerToStartID, playerToStart);
    }

    @PatchMapping("dropPlayer/{playerID}")
    public Player dropPlayerFromTeam(@PathVariable("playerID") int id){
        var player = playerService.getPlayerById(id);
        player.setTeam_id(0);
        player.setStarting(false);
        return playerService.updatePlayerById(id, player);
    }

    @GetMapping
    public List<Player> getPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/freeAgents")
    public List<Player> getFreeAgents(){
        var allPlayers = playerService.getAllPlayers();
        var freeAgents = new ArrayList<Player>();

        for (Player player: allPlayers) {
            if (player.getTeam_id() == 0) {
                freeAgents.add((player));
            }
        }
        return freeAgents;
    }

    @GetMapping("/position/{position}")
    public List<Player> getPlayerByPosition(@PathVariable("position") String pos){
        var allPlayers = playerService.getAllPlayers();
        var players = new ArrayList<Player>();

        for (Player player: allPlayers) {
            if (Objects.equals(player.getPosition(), pos)) {
                players.add((player));
            }
        }
        return players;
    }

    @GetMapping("/team/{team}")
    public List<Player> getPlayerByTeam(@PathVariable("team") String team){
        var allPlayers = playerService.getAllPlayers();
        var players = new ArrayList<Player>();

        for (Player player: allPlayers) {
            if (player.getTeam_city().toLowerCase().contains(team.toLowerCase())) {
                players.add((player));
            }
            else if (player.getTeam_name().toLowerCase().contains(team.toLowerCase())) {
                players.add((player));
            }
        }
        return players;
    }

    @GetMapping("/name/{name}")
    public List<Player> getPlayerByName(@PathVariable("name") String name){
        var allPlayers = playerService.getAllPlayers();
        var players = new ArrayList<Player>();

        for (Player player: allPlayers) {
            if (player.getFirst_name().toLowerCase().contains(name.toLowerCase())) {
                players.add((player));
            }
            else if (player.getLast_name().toLowerCase().contains(name.toLowerCase())) {
                players.add((player));
            }
        }
        return players;
    }

    @GetMapping("userTeam/{id}")
    public List<Player> getPlayerByUserTeamId(@PathVariable("id") int id){
        var allPlayers = playerService.getAllPlayers();
        var starters = new ArrayList<Player>();
        var startQB = new ArrayList<Player>();
        var startRBS = new ArrayList<Player>();
        var startWRS = new ArrayList<Player>();
        var startTE = new ArrayList<Player>();
        var startK = new ArrayList<Player>();

        for (Player player: allPlayers) {
            if (player.getTeam_id() == id && player.isStarting()) {
                if (Objects.equals(player.getPosition(), "QB")) {
                    startQB.add(player);
                }
                else if (Objects.equals(player.getPosition(), "RB")) {
                    startRBS.add(player);
                }
                else if (Objects.equals(player.getPosition(), "WR")) {
                    startWRS.add(player);
                }
                else if (Objects.equals(player.getPosition(), "TE")) {
                    startTE.add(player);
                }
                else if (Objects.equals(player.getPosition(), "RB")) {
                    startRBS.add(player);
                }
                else if (Objects.equals(player.getPosition(), "K")) {
                    startK.add(player);
                }
            }
        }

        starters.addAll(startQB);
        starters.addAll(startRBS);
        starters.addAll(startWRS);
        starters.addAll(startTE);
        starters.addAll(startK);
        return starters;
    }

    @GetMapping("userTeam/bench/{id}")
    public List<Player> getPlayerByUserTeamBenchId(@PathVariable("id") int id){
        var allPlayers = playerService.getAllPlayers();
        var bench = new ArrayList<Player>();

        for (Player player: allPlayers) {
            if (player.getTeam_id() == id && !player.isStarting()) {
                bench.add(player);
            }
        }
        return bench;
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable("id") int id){
        return playerService.getPlayerById(id);
    }
}
