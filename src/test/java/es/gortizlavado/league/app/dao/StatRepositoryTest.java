package es.gortizlavado.league.app.dao;

import es.gortizlavado.league.app.entity.Player;
import es.gortizlavado.league.app.entity.Stats;
import es.gortizlavado.league.app.entity.StatsId;
import es.gortizlavado.league.app.models.enums.Position;
import es.gortizlavado.league.app.models.enums.Status;
import es.gortizlavado.league.app.models.enums.Team;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.flyway.OptimizedFlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        OptimizedFlywayTestExecutionListener.class})
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
class StatRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldFetchPlayerStatsWithAllData_whenPlayerAlreadyStored() {
        final UUID idPlayerNew = UUID.randomUUID();
        final Player player = Player.builder()
                .id(idPlayerNew)
                .name("footballer-one")
                .dateOfBirthday(LocalDate.of(1992, 5, 29))
                .position(Position.DEFENSE)
                .team(Team.CD_BADAJOZ)
                .status(Status.HURT).build();
        playerRepository.save(player);

        final Stats stats = Stats.builder()
                .season("2020/2021")
                .player(player)
                .idPlayer(idPlayerNew)
                .points(10)
                .goals(1)
                .redCards(1)
                .build();
        statsRepository.save(stats);

        final Optional<Player> playerOptional = playerRepository.findById(idPlayerNew);
        Assertions.assertTrue(playerOptional.isPresent());

        final Optional<Stats> statsOptional = statsRepository.findById(StatsId.builder()
                .idPlayer(idPlayerNew)
                .season("2020/2021")
                .build());
        Assertions.assertTrue(statsOptional.isPresent());
        Assertions.assertNotNull(statsOptional.get().getPlayer());
        Assertions.assertEquals("footballer-one", statsOptional.get().getPlayer().getName());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldFetchPlayerStatsWithAllData_whenCreateNewStatsForAPlayer() {
        final UUID idPlayerNew = UUID.randomUUID();
        final Player player = Player.builder()
                .id(idPlayerNew)
                .name("footballer-one")
                .dateOfBirthday(LocalDate.of(1992, 5, 29))
                .position(Position.DEFENSE)
                .team(Team.CD_BADAJOZ)
                .status(Status.HURT).build();

        final Stats stats = Stats.builder()
                .season("2020/2021")
                .player(player)
                .idPlayer(idPlayerNew)
                .points(10)
                .goals(1)
                .redCards(1)
                .build();
        statsRepository.save(stats);

        final Optional<Player> playerOptional = playerRepository.findById(idPlayerNew);
        Assertions.assertTrue(playerOptional.isPresent());

        final Optional<Stats> statsOptional = statsRepository.findById(StatsId.builder()
                .idPlayer(idPlayerNew)
                .season("2020/2021")
                .build());
        Assertions.assertTrue(statsOptional.isPresent());
        Assertions.assertNotNull(statsOptional.get().getPlayer());
        Assertions.assertEquals("footballer-one", statsOptional.get().getPlayer().getName());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldChangeThePlayerInfo_whenSaveStatsWithThePlayerNewInfo() {
        final UUID idPlayer = UUID.fromString("00000001-f1b1-11ec-8ea0-0242ac120002");
        final Player player = playerRepository.findById(idPlayer).orElseThrow();

        final String newLastname = "new lastname";
        player.setLastname(newLastname);

        final Stats stats = Stats.builder()
                .season("2020/2021")
                .player(player)
                .idPlayer(idPlayer)
                .points(10)
                .goals(1)
                .redCards(1)
                .build();
        statsRepository.save(stats);

        final Optional<Player> playerOptional = playerRepository.findById(idPlayer);
        Assertions.assertTrue(playerOptional.isPresent());
        Assertions.assertEquals(newLastname, playerOptional.get().getLastname());
    }

    @Test
    @FlywayTest
    void shouldNotFetchStats_whenJustPlayerInfoStored() {
        final UUID idPlayer = UUID.randomUUID();
        final Player player = Player.builder()
                .id(idPlayer)
                .name("footballer-one")
                .dateOfBirthday(LocalDate.of(1992, 5, 29))
                .position(Position.DEFENSE)
                .team(Team.CD_BADAJOZ)
                .status(Status.HURT).build();
        playerRepository.save(player);

        final Optional<Stats> statsOptional = statsRepository.findById(StatsId.builder()
                .idPlayer(idPlayer)
                .season("2020/2021")
                .build());
        Assertions.assertFalse(statsOptional.isPresent());
    }

}