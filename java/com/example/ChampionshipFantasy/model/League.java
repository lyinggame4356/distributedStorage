package com.example.ChampionshipFantasy.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class League extends AuditModel {

    @Id
    private Long id;
    private String name;

    @ManyToMany(
            targetEntity=com.example.ChampionshipFantasy.model.FantasyTeam.class,
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name="league_fantasy_team",
            joinColumns=@JoinColumn(name="league_id"),
            inverseJoinColumns=@JoinColumn(name="fant_team_id")
    )
    private List<FantasyTeam> fantasyTeams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FantasyTeam> getFantasyTeams() {
        return fantasyTeams;
    }

    public void setFantasyTeams(List<FantasyTeam> fantasyTeams) {
        this.fantasyTeams = fantasyTeams;
    }
}
