package com.example.ChampionshipFantasy.repository;

import com.example.ChampionshipFantasy.model.player.Player;
import com.example.ChampionshipFantasy.model.PlayerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAll();

    @Query("SELECT new com.example.ChampionshipFantasy.model.PlayerSummary(id, name) FROM Player")
    List<PlayerSummary> findSummary();
}
