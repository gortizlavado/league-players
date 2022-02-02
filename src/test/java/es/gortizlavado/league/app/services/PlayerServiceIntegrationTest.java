package es.gortizlavado.league.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import es.gortizlavado.league.app.dao.PlayerRepository;
import es.gortizlavado.league.app.dao.StatsRepository;
import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.mapper.PlayerMapper;
import es.gortizlavado.league.app.models.dto.PlayerDTO;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith({SpringExtension.class/*, FlywayTestExtension.class*/})
@SpringBootTest
@AutoConfigureEmbeddedDatabase
class PlayerServiceIntegrationTest {

    @Autowired
    private PlayerService service;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        prepareDataInDDBB();
    }

    //TODO failed with the version of the io.zonky.test:embedded-database-spring-test
    // @FlywayTest(locationsForMigrate = "/db/migration")
    @Test
    void shouldReturnPlayerInAnySeason_whenStoredInDataBase() {
        statsRepository.findAll();
        final PlayerDTO playerDTO = service.fetchPlayerStatById(2L, "2020/2021");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    void shouldPartialUpdatePlayer() throws IOException {

        JsonPatch patch = JsonPatch.fromJson(objectMapper.readTree("[" +
                "{ \"op\": \"replace\", \"path\": \"/points\", \"value\": 15 },\n" +
                "{ \"op\": \"replace\", \"path\": \"/name\", \"value\": \"footballer-new\" },\n" +
                "{ \"op\": \"replace\", \"path\": \"/matchPlayed\", \"value\": 4 }\n" +
                "]"));

        final PlayerDTO playerDTO = service.updatePlayer(2L, "2020/2021", patch);

        PlayerDTO expectedPlayerDTO = PlayerDTO.builder()
                .id("2")
                .name("footballer-new")
                .lastname("lastname-two")
                .surname("surname-two")
                .fullName("footballer-new lastname-two surname-two")
                .dateOfBirthday(LocalDate.of(1989, 11, 9).toString())
                .age(32)
                .position(Position.GOALKEEPER.name())
                .team(Team.CD_DON_BENITO.name())
                .status(Status.ACTIVE.name())
                .season("2020/2021")
                .points(15)
                .matchPlayed(4)
                .goals(1)
                .goalsConceded(0)
                .goalsByPenalty(0)
                .yellowCards(0)
                .doubleYellowCards(0)
                .redCards(1)
                .build();

        Assertions.assertEquals(expectedPlayerDTO, playerDTO);
        Assertions.assertEquals(2, statsRepository.count());
    }

    private void prepareDataInDDBB() {
        final List<Player> players = List.of(Player.builder()
                        .id(1L)
                        .name("footballer-one")
                        .dateOfBirthday(LocalDate.of(1992, 5, 29))
                        .position(Position.DEFENSE)
                        .team(Team.CD_BADAJOZ)
                        .status(Status.HURT).build(),
                Player.builder()
                        .id(2L)
                        .name("footballer-two")
                        .lastname("lastname-two")
                        .surname("surname-two")
                        .dateOfBirthday(LocalDate.of(1989, 11, 9))
                        .position(Position.GOALKEEPER)
                        .team(Team.CD_DON_BENITO)
                        .status(Status.ACTIVE).build());

        final List<Stats> stats = List.of(Stats.builder()
                        .season("2020/2021")
                        .idPlayer(2L)
                        .points(10)
                        .matchPlayed(3)
                        .goals(1)
                        .redCards(1)
                        .build(),
                Stats.builder()
                        .season("2019/2020")
                        .idPlayer(2L)
                        .points(-6)
                        .goals(0)
                        .yellowCards(2)
                        .redCards(1)
                        .build());

        playerRepository.saveAll(players);
        statsRepository.saveAll(stats);
    }

}