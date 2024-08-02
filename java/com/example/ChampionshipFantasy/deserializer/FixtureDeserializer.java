package com.example.ChampionshipFantasy.deserializer;

import com.example.ChampionshipFantasy.model.*;
import com.example.ChampionshipFantasy.model.Event;
import com.example.ChampionshipFantasy.service.RepositoryService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FixtureDeserializer extends JsonDeserializer<Fixture> {

    private RepositoryService repositoryService;

    FixtureDeserializer() {
        this.repositoryService = RepositoryService.instance;
    }

    @Override
    public Fixture deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);

        long id = node.get("id").asLong();
        Gameweek gameweek = repositoryService.getGameweekReference(node.get("round_id").asLong());
        Team homeTeam = repositoryService.getTeamReference(node.get("localteam_id").asLong());
        Team awayTeam = repositoryService.getTeamReference(node.get("visitorteam_id").asLong());
        FixtureStatus fixtureStatus = FixtureStatus.valueOf(node.get("time").get("status").textValue());

        Integer minutesPlayed = node.get("time").get("minute").asInt();

        List<LineUp> lineUps = Arrays.asList(mapper.readValue(node.get("lineup").get("data").traverse(), LineUp[].class));

        Fixture fixture = new Fixture(id, gameweek, homeTeam, awayTeam, fixtureStatus, minutesPlayed);
        fixture.setLineUps(lineUps);
        //tidy up

        try {
            List<Event> events = Arrays.asList(mapper.readValue(node.get("events").get("data").traverse(), Event[].class));
            for (Event event : events) {
                event.setGameweek(gameweek);
                event.setFixture(fixture);
            }

            fixture.setEvents(events);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return fixture;
    }
}
