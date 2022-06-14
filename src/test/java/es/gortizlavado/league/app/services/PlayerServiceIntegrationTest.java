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
import io.zonky.test.db.flyway.OptimizedFlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        OptimizedFlywayTestExecutionListener.class,
        MockitoTestExecutionListener.class})
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
class PlayerServiceIntegrationTest {

    @Autowired
    private PlayerService service;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private PlayerMapper playerMapper;

    @Captor
    ArgumentCaptor<Stats> statsCaptor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldReturnPlayerInAnySeason_whenStoredInDataBase() {
        final UUID idPlayer = UUID.fromString("00000002-f1b1-11ec-8ea0-0242ac120002");
        final PlayerDTO playerDTO = service.fetchPlayerStatById(idPlayer, "2020/2021");
        Assertions.assertNotNull(playerDTO);
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldPartialUpdatePlayer() throws IOException {

        JsonPatch patch = JsonPatch.fromJson(objectMapper.readTree("[" +
                "{ \"op\": \"replace\", \"path\": \"/points\", \"value\": 15 },\n" +
                "{ \"op\": \"replace\", \"path\": \"/name\", \"value\": \"footballer-new\" },\n" +
                "{ \"op\": \"replace\", \"path\": \"/matchPlayed\", \"value\": 4 }\n" +
                "]"));

        final UUID idPlayer = UUID.fromString("00000002-f1b1-11ec-8ea0-0242ac120002");
        final PlayerDTO playerDTO = service.updatePlayer(idPlayer, "2020/2021", patch);

        PlayerDTO expectedPlayerDTO = PlayerDTO.builder()
                .id(idPlayer.toString())
                .name("footballer-new")
                .lastname("lastname2")
                .surname("surname2")
                .fullName("footballer-new lastname2 surname2")
                .dateOfBirthday(LocalDate.of(1989, 11, 9).toString())
                .age(32)
                .position(Position.FORWARD.name())
                .team(Team.MERIDA_AD.name())
                .status(Status.ACTIVE.name())
                .season("2020/2021")
                .points(15)
                .matchPlayed(4)
                .goals(4)
                .goalsConceded(0)
                .goalsByPenalty(1)
                .yellowCards(1)
                .doubleYellowCards(0)
                .redCards(0)
                .build();

        Assertions.assertEquals(expectedPlayerDTO, playerDTO);

        final Optional<Player> playerRepositoryById = playerRepository.findById(idPlayer);
        Assertions.assertNotNull(playerRepositoryById.orElseThrow().getUpdated_at());
    }

}
