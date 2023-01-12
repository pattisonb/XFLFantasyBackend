package com.brian.xflfantasy.services;

import com.brian.xflfantasy.models.Player;
import com.brian.xflfantasy.repositories.PlayerRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {

    @Autowired
    private final PlayerRepo playerRepo;

    public Player createPlayer(Player player){
        return playerRepo.save(player);
    }

    public void deletePlayerById(int playerId){
        playerRepo.deleteById(playerId);
    }

    public Player updatePlayerById(int playerId, Player player){
        player.setId(playerId);
        return playerRepo.save(player);
    }

    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }
    public Player getPlayerById(int playerId){
        return playerRepo.getReferenceById(playerId);
    }
}
